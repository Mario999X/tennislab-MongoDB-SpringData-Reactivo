package resa.mendoza.controller

/**
 * @author Mario Resa y Sebastián Mendoza
 */

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import resa.mendoza.dto.toUsuario
import resa.mendoza.dto.toUsuarioDto
import resa.mendoza.models.ResponseFailure
import resa.mendoza.models.ResponseSuccess
import resa.mendoza.models.Usuario
import resa.mendoza.repositories.UsuariosCacheRepository
import resa.mendoza.repositories.UsuariosKtorFitRepository
import resa.mendoza.repositories.UsuariosMongoRepository

private val logger = KotlinLogging.logger { }

/**
 * Controlador encargado de los usuarios, tanto del servicio externo, como de MongoDB y de la cache.
 *
 * @property usuariosMongoRepository
 * @property usuariosKtorFitRepository
 * @property usuariosCacheRepository
 */
@Controller
class UsuarioController
@Autowired constructor(
    private val usuariosMongoRepository: UsuariosMongoRepository,
    private val usuariosKtorFitRepository: UsuariosKtorFitRepository,
    private val usuariosCacheRepository: UsuariosCacheRepository
) {

    suspend fun getAllUsuariosApi(): Flow<Usuario> = withContext(Dispatchers.IO) {
        logger.info { "Obteniendo usuarios API" }
        val listado = mutableListOf<Usuario>()

        usuariosKtorFitRepository.findAll().collect { listado.add(it.toUsuario("Hola1")) }

        println(Json.encodeToString(ResponseSuccess(200, listado.toString())))
        return@withContext listado.asFlow()
    }

    suspend fun getAllUsuariosMongo(): Flow<Usuario> {
        logger.info { "Obteniendo usuarios MONGO" }
        val response = usuariosMongoRepository.findAll()

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    suspend fun getAllUsuariosCache(): Flow<Usuario> {
        logger.info { "Obteniendo usuarios CACHE" }
        val response = usuariosCacheRepository.findAll()

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    suspend fun createUsuario(entity: Usuario): Usuario = withContext(Dispatchers.IO) {
        logger.info { "Save usuario $entity" }
        launch {
            usuariosMongoRepository.save(entity)
        }

        launch {
            usuariosCacheRepository.save(entity)
        }

        joinAll()

        println(Json.encodeToString(ResponseSuccess(201, entity.toUsuarioDto())))
        return@withContext entity
    }

    suspend fun getUsuarioById(id: ObjectId): Usuario? {
        logger.info { "Obteniendo usuario con id $id" }
        var userSearch = usuariosCacheRepository.findById(id)
        if (userSearch != null) {
            println(Json.encodeToString(ResponseSuccess(200, userSearch.toUsuarioDto())))

        } else {
            userSearch = usuariosMongoRepository.findById(id)
            if (userSearch != null) {
                println(Json.encodeToString(ResponseSuccess(201, userSearch.toUsuarioDto())))
            } else System.err.println(Json.encodeToString(ResponseFailure(404, "User not found")))
        }

        return userSearch
    }

    suspend fun deleteUsuario(entity: Usuario) = withContext(Dispatchers.IO) {
        logger.info { "Borrando usuario $entity" }
        launch {
            usuariosCacheRepository.delete(entity)
        }

        launch {
            usuariosMongoRepository.delete(entity)
        }

        joinAll()

        println(Json.encodeToString(ResponseSuccess(200, entity.toUsuarioDto())))
    }

    suspend fun resetUsuariosMongo() {
        usuariosMongoRepository.deleteAll()
    }

}