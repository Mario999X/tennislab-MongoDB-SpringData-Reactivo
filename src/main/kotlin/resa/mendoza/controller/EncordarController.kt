package resa.mendoza.controller

import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import resa.mendoza.models.Encordar
import resa.mendoza.repositories.EncordarRepository

private val logger = KotlinLogging.logger { }

@Controller
class EncordarController
@Autowired constructor(
    private val encordarRepository: EncordarRepository
) {
    fun getEncordaciones(): Flow<Encordar> {
        logger.info { "Obteniendo encordaciones" }
        return encordarRepository.findAll()
    }

    suspend fun createEncordar(entity: Encordar): Encordar {
        logger.info { "Creando encordacion $entity" }
        encordarRepository.save(entity)
        return entity
    }

    suspend fun getEncordarById(id: ObjectId): Encordar? {
        logger.info { "Obteniendo encordacion con id $id" }
        return encordarRepository.findById(id)
    }

    suspend fun deleteEncordacion(entity: Encordar) {
        logger.info { "Borrando encordacion $entity" }
        encordarRepository.delete(entity)
    }

    suspend fun resetEncordaciones() {
        logger.info { "Borrando encordaciones" }
        encordarRepository.deleteAll()
    }
}