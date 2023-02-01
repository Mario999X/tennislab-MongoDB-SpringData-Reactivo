package resa.mendoza.controller

import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import resa.mendoza.models.Tarea
import resa.mendoza.repositories.TareaRepository

private val logger = KotlinLogging.logger { }

@Controller
class TareaController
@Autowired constructor(
    private val tareaRepository: TareaRepository
) {
    fun getTareas(): Flow<Tarea> {
        logger.info { "Obteniendo tareas" }
        return tareaRepository.findAll()
    }

    suspend fun createTarea(entity: Tarea): Tarea {
        logger.info { "Creando tarea $entity" }
        return tareaRepository.save(entity)
    }

    suspend fun getTareaById(id: ObjectId): Tarea? {
        logger.info { "Obteniendo tarea con id $id" }
        return tareaRepository.findById(id)
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