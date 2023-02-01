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
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import resa.mendoza.dto.toUsuario
import resa.mendoza.models.Usuario
import resa.mendoza.repositories.UsuariosCacheRepository
import resa.mendoza.repositories.UsuariosKtorFitRepository
import resa.mendoza.repositories.UsuariosMongoRepository

private val logger = KotlinLogging.logger { }

/**
 * Controller de Usuarios
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

        return@withContext listado.asFlow()
    }

    suspend fun getAllUsuariosMongo(): Flow<Usuario> {
        logger.info { "Obteniendo usuarios MONGO" }
        return usuariosMongoRepository.findAll()
    }

    suspend fun getAllUsuariosCache(): Flow<Usuario> {
        logger.info { "Obteniendo usuarios CACHE" }
        return usuariosCacheRepository.findAll()
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
        return@withContext entity
    }

    suspend fun getUsuarioById(id: ObjectId): Usuario? {
        logger.info { "Obteniendo usuario con id $id" }
        var userSearch = usuariosCacheRepository.findById(id)
        if (userSearch != null) {
            println("Buscando en Mongo...")

        } else {
            userSearch = usuariosMongoRepository.findById(id)
            if (userSearch != null) {
                println("Usuario no encontrado")

            } else System.err.println("Usuario no encontrado")
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
    }

    suspend fun resetUsuariosMongo() {
        usuariosMongoRepository.deleteAll()
    }

}