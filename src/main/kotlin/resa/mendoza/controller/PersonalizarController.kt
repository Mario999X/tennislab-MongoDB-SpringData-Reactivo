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
import resa.mendoza.dto.toPersonalizarDto
import resa.mendoza.models.Personalizar
import resa.mendoza.models.ResponseFailure
import resa.mendoza.models.ResponseSuccess
import resa.mendoza.repositories.PersonalizarRepository

private val logger = KotlinLogging.logger { }

/**
 * Controlador al que se le pasa un repositorio, el cual contiene el CRUD para las personalizaciones
 * @param personalizarRepository
 */
@Controller
class PersonalizarController
@Autowired constructor(
    private val personalizarRepository: PersonalizarRepository
) {

    fun getPersonalizaciones(): Flow<Personalizar> {
        logger.info { "Obteniendo personalizaciones" }
        val response = personalizarRepository.findAll()

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    suspend fun createPersonalizaciones(entity: Personalizar): Personalizar {
        logger.info { "Creando personalizacion" }
        val response = personalizarRepository.save(entity)

        println(Json.encodeToString(ResponseSuccess(201, response.toPersonalizarDto())))
        return response
    }

    suspend fun getPersonalizacionById(id: ObjectId): Personalizar? {
        logger.info { "Obteniendo personalizacion con id $id" }
        val response = personalizarRepository.findById(id)

        if (response == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Personalizacion not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, response.toPersonalizarDto())))

        return response
    }

    suspend fun deletePersonalizacion(entity: Personalizar) {
        logger.info { "Borrando personalizacion $entity" }

        println(Json.encodeToString(ResponseSuccess(200, entity.toPersonalizarDto())))
        personalizarRepository.delete(entity)
    }

    suspend fun resetPersonalizaciones() {
        logger.info { "Borrando personalizaciones" }
        personalizarRepository.deleteAll()
    }
}