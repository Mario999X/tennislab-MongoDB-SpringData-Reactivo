package resa.mendoza.controller

import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import resa.mendoza.models.Personalizar
import resa.mendoza.repositories.PersonalizarRepository

private val logger = KotlinLogging.logger { }

@Controller
class PersonalizarController
@Autowired constructor(
    private val personalizarRepository: PersonalizarRepository
) {

    fun getPersonalizaciones(): Flow<Personalizar> {
        logger.info { "Obteniendo personalizaciones" }
        return personalizarRepository.findAll()
    }

    suspend fun createPersonalizaciones(entity: Personalizar): Personalizar {
        logger.info { "Creando personalizacion" }
        personalizarRepository.save(entity)
        return entity
    }

    suspend fun getPersonalizacionById(id: ObjectId): Personalizar? {
        logger.info { "Obteniendo personalizacion con id $id" }
        return personalizarRepository.findById(id)
    }

    suspend fun deletePersonalizacion(entity: Personalizar) {
        logger.info { "Borrando personalizacion $entity" }
        personalizarRepository.delete(entity)
    }

    suspend fun resetPersonalizaciones() {
        logger.info { "Borrando personalizaciones" }
        personalizarRepository.deleteAll()
    }
}