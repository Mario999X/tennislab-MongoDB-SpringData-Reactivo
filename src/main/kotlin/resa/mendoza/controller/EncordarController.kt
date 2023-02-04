package resa.mendoza.controller

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import resa.mendoza.dto.toEncordarDto
import resa.mendoza.models.Encordar
import resa.mendoza.models.ResponseFailure
import resa.mendoza.models.ResponseSuccess
import resa.mendoza.repositories.EncordarRepository

private val logger = KotlinLogging.logger { }

/**
 * Controlador al que se le pasa un repositorio, el cual contiene el CRUD para los encordados
 *
 * @property encordarRepository
 */
@Controller
class EncordarController
@Autowired constructor(
    private val encordarRepository: EncordarRepository
) {
    fun getEncordaciones(): Flow<Encordar> {
        logger.info { "Obteniendo encordaciones" }
        val response = encordarRepository.findAll()

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    suspend fun createEncordar(entity: Encordar): Encordar {
        logger.info { "Creando encordacion $entity" }
        val response = encordarRepository.save(entity)

        println(Json.encodeToString(ResponseSuccess(201, response.toEncordarDto())))
        return response
    }

    suspend fun getEncordarById(id: ObjectId): Encordar? {
        logger.info { "Obteniendo encordacion con id $id" }
        val response = encordarRepository.findById(id)

        if (response == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Encordado not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, response.toEncordarDto())))
        return response
    }

    suspend fun deleteEncordacion(entity: Encordar) {
        logger.info { "Borrando encordacion $entity" }

        println(Json.encodeToString(ResponseSuccess(200, entity.toEncordarDto())))
        encordarRepository.delete(entity)
    }

    suspend fun resetEncordaciones() {
        logger.info { "Borrando encordaciones" }
        encordarRepository.deleteAll()
    }
}