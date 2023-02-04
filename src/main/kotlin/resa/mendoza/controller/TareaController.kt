package resa.mendoza.controller

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import resa.mendoza.dto.toTareaDto
import resa.mendoza.models.Perfil
import resa.mendoza.models.ResponseFailure
import resa.mendoza.models.ResponseSuccess
import resa.mendoza.models.Tarea
import resa.mendoza.repositories.TareaRepository
import resa.mendoza.repositories.TareasKtorFitRepository

private val logger = KotlinLogging.logger { }

/**
 * Controlador al que se le pasan dos repositorios, uno se encarga de realizar CRUD de MongoDB y el otro, a subir las tareas
 * a un servicio externo.
 *
 * @property tareaRepository
 * @property tareasKtorFitRepository
 */
@Controller
class TareaController
@Autowired constructor(
    private val tareaRepository: TareaRepository,
    private val tareasKtorFitRepository: TareasKtorFitRepository
) {
    fun getTareas(): Flow<Tarea> {
        logger.info { "Obteniendo tareas" }
        val response = tareaRepository.findAll()

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    suspend fun createTarea(entity: Tarea): Tarea = withContext(Dispatchers.IO) {
        logger.info { "Creando tarea $entity" }
        if (entity.usuario.perfil == Perfil.ENCORDADOR) {
            launch {
                tareaRepository.save(entity)
            }

            launch {
                tareasKtorFitRepository.uploadTarea(entity)
            }

            joinAll()
            println(Json.encodeToString(ResponseSuccess(201, entity.toTareaDto())))

        } else System.err.println(
            Json.encodeToString(
                ResponseFailure(
                    400,
                    "No ha sido posible almacenar $entity || El usuario debe de ser de tipo ${Perfil.ENCORDADOR.name}"
                )
            )
        )
        return@withContext entity
    }

    suspend fun getTareaById(id: ObjectId): Tarea? {
        logger.info { "Obteniendo tarea con id $id" }
        val response = tareaRepository.findById(id)

        if (response == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Tarea not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, response.toTareaDto())))
        return response
    }

    suspend fun deleteTarea(entity: Tarea) {
        logger.info { "Borrando tarea $entity" }
        tareaRepository.delete(entity)
    }

    suspend fun resetTarea() {
        logger.info { "Borrando tareas" }
        tareaRepository.deleteAll()
    }
}