package resa.mendoza.controller

import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import resa.mendoza.models.maquina.Personalizadora
import resa.mendoza.repositories.MaquinaPersonalizadoraRepository

private val logger = KotlinLogging.logger { }

@Controller
class MaquinaPersonalizadoraController
@Autowired constructor(
    private val maquinaPersonalizadoraRepository: MaquinaPersonalizadoraRepository
) {
    fun getPersonalizadoras(): Flow<Personalizadora> {
        logger.info { "Obteniendo máquinas personalizadoras" }
        return maquinaPersonalizadoraRepository.findAll()
    }

    suspend fun createPersonalizadora(entity: Personalizadora): Personalizadora {
        logger.info { "Creando máquina personalizadora $entity" }
        return maquinaPersonalizadoraRepository.save(entity)
    }

    suspend fun getPersonalizadoraById(id: ObjectId): Personalizadora? {
        logger.info { "Obteniendo personalizadora con id $id" }
        return maquinaPersonalizadoraRepository.findById(id)
    }

    suspend fun deletePersonalizadora(entity: Personalizadora) {
        logger.info { "Borrando máquina personalizadora $entity" }
        maquinaPersonalizadoraRepository.delete(entity)
    }

    suspend fun resetPersonalizadoras() {
        logger.info { "Borrando maquinas personalizadoras" }
        maquinaPersonalizadoraRepository.deleteAll()
    }
}