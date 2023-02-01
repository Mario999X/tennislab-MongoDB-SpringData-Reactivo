package resa.mendoza.controller

import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import resa.mendoza.models.Adquisicion
import resa.mendoza.repositories.AdquisicionRepository


private val logger = KotlinLogging.logger { }

@Controller
class AdquisicionController
@Autowired constructor(
    private val adquisicionRepository: AdquisicionRepository
) {
    fun getAdquisiciones(): Flow<Adquisicion> {
        logger.info { "Obteniendo adquisiciones" }
        return adquisicionRepository.findAll()
    }

    suspend fun createAdquisicion(entity: Adquisicion): Adquisicion {
        logger.info { "Creando adquisicion $entity" }
        return adquisicionRepository.save(entity)
    }

    suspend fun getAdquisicionById(id: ObjectId): Adquisicion? {
        logger.info { "Obteniendo adquisicion con id $id" }
        return adquisicionRepository.findById(id)
    }

    suspend fun deleteAdquisicion(entity: Adquisicion) {
        logger.info { "Borrando adquisicion $entity" }
        adquisicionRepository.delete(entity)
    }

    suspend fun resetAdquisiciones() {
        logger.info { "Borrando adquisiciones" }
        adquisicionRepository.deleteAll()
    }
}