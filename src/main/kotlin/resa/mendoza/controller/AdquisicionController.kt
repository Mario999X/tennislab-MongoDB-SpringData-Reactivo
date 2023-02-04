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
import resa.mendoza.dto.toAdquisicionDto
import resa.mendoza.models.Adquisicion
import resa.mendoza.models.ResponseFailure
import resa.mendoza.models.ResponseSuccess
import resa.mendoza.repositories.AdquisicionRepository


private val logger = KotlinLogging.logger { }

/**
 * Controlador al que se le pasa un repositorio, el cual contiene el CRUD para las adquisiciones
 * @param adquisicionRepository
 */
@Controller
class AdquisicionController
@Autowired constructor(
    private val adquisicionRepository: AdquisicionRepository
) {
    fun getAdquisiciones(): Flow<Adquisicion> {
        logger.info { "Obteniendo adquisiciones" }
        val response = adquisicionRepository.findAll()

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    suspend fun createAdquisicion(entity: Adquisicion): Adquisicion {
        logger.info { "Creando adquisicion $entity" }
        val response = adquisicionRepository.save(entity)

        println(Json.encodeToString(ResponseSuccess(201, response.toAdquisicionDto())))
        return response
    }

    suspend fun getAdquisicionById(id: ObjectId): Adquisicion? {
        logger.info { "Obteniendo adquisicion con id $id" }
        val response = adquisicionRepository.findById(id)

        if (response == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Adquisicion not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, response.toAdquisicionDto())))
        return response
    }

    suspend fun deleteAdquisicion(entity: Adquisicion) {
        logger.info { "Borrando adquisicion $entity" }

        println(Json.encodeToString(ResponseSuccess(200, entity.toAdquisicionDto())))
        adquisicionRepository.delete(entity)
    }

    suspend fun resetAdquisiciones() {
        logger.info { "Borrando adquisiciones" }
        adquisicionRepository.deleteAll()
    }
}