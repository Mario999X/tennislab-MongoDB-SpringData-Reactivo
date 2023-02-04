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
import resa.mendoza.dto.toEncordadoraDto
import resa.mendoza.models.Perfil
import resa.mendoza.models.ResponseFailure
import resa.mendoza.models.ResponseSuccess
import resa.mendoza.models.maquina.Encordadora
import resa.mendoza.repositories.MaquinaEncordadoraRepository

private val logger = KotlinLogging.logger { }

/**
 * Controlador al que se le pasa un repositorio, el cual contiene el CRUD para las encordadoras
 * @param maquinaEncordadoraRepository
 */
@Controller
class MaquinaEncordadoraController
@Autowired constructor(
    private val maquinaEncordadoraRepository: MaquinaEncordadoraRepository
) {

    fun getEncordadoras(): Flow<Encordadora> {
        logger.info { "Obteniendo máquinas encordadoras" }
        val response = maquinaEncordadoraRepository.findAll()

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    suspend fun createEncordadora(entity: Encordadora): Encordadora {
        logger.info { "Creando máquina encordadora $entity" }
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
        } else println(Json.encodeToString(ResponseSuccess(200, entity.toEncordadoraDto())))

        return maquinaEncordadoraRepository.save(entity)
    }

    suspend fun getEncordadoraById(id: ObjectId): Encordadora? {
        logger.info { "Obteniendo máquina encordadora con id $id" }
        val response = maquinaEncordadoraRepository.findById(id)

        if (response == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Encordadora not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, response.toEncordadoraDto())))

        return response
    }

    suspend fun deleteEncordadora(entity: Encordadora) {
        logger.info { "Borrando máquina encordadora $entity" }

        println(Json.encodeToString(ResponseSuccess(200, entity.toEncordadoraDto())))
        maquinaEncordadoraRepository.delete(entity)
    }

    suspend fun resetEncordadoras() {
        logger.info { "Borrando máquinas encordadoras" }
        maquinaEncordadoraRepository.deleteAll()
    }
}