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
import resa.mendoza.dto.toPersonalizadoraDto
import resa.mendoza.models.Perfil
import resa.mendoza.models.ResponseFailure
import resa.mendoza.models.ResponseSuccess
import resa.mendoza.models.maquina.Personalizadora
import resa.mendoza.repositories.MaquinaPersonalizadoraRepository

private val logger = KotlinLogging.logger { }

/**
 * Controlador al que se le pasa un repositorio, el cual contiene el CRUD para las encordadoras
 * @param maquinaPersonalizadoraRepository
 */
@Controller
class MaquinaPersonalizadoraController
@Autowired constructor(
    private val maquinaPersonalizadoraRepository: MaquinaPersonalizadoraRepository
) {
    fun getPersonalizadoras(): Flow<Personalizadora> {
        logger.info { "Obteniendo máquinas personalizadoras" }
        val response = maquinaPersonalizadoraRepository.findAll()

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    suspend fun createPersonalizadora(entity: Personalizadora): Personalizadora {
        logger.info { "Creando máquina personalizadora $entity" }
        if (entity.turno?.trabajador?.perfil != Perfil.ENCORDADOR && entity.turno != null) {
            System.err.println(
                Json.encodeToString(
                    ResponseFailure(
                        400,
                        "Problema al crear el turno, el usuario debe de ser de tipo ${Perfil.ENCORDADOR}"
                    )
                )
            )
            entity.turno = null
        } else println(Json.encodeToString(ResponseSuccess(200, entity.toPersonalizadoraDto())))

        return maquinaPersonalizadoraRepository.save(entity)
    }

    suspend fun getPersonalizadoraById(id: ObjectId): Personalizadora? {
        logger.info { "Obteniendo personalizadora con id $id" }
        val response = maquinaPersonalizadoraRepository.findById(id)

        if (response == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Personalizadora not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, response.toPersonalizadoraDto())))
        return response
    }

    suspend fun deletePersonalizadora(entity: Personalizadora) {
        logger.info { "Borrando máquina personalizadora $entity" }

        println(Json.encodeToString(ResponseSuccess(200, entity.toPersonalizadoraDto())))
        maquinaPersonalizadoraRepository.delete(entity)
    }

    suspend fun resetPersonalizadoras() {
        logger.info { "Borrando maquinas personalizadoras" }
        maquinaPersonalizadoraRepository.deleteAll()
    }
}