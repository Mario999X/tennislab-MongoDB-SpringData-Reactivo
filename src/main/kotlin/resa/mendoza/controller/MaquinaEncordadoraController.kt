package resa.mendoza.controller

import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import resa.mendoza.models.maquina.Encordadora
import resa.mendoza.repositories.MaquinaEncordadoraRepository

private val logger = KotlinLogging.logger { }

@Controller
class MaquinaEncordadoraController
@Autowired constructor(
    private val maquinaEncordadoraRepository: MaquinaEncordadoraRepository
) {

    fun getEncordadoras(): Flow<Encordadora> {
        logger.info { "Obteniendo máquinas encordadoras" }
        return maquinaEncordadoraRepository.findAll()
    }

    suspend fun createEncordadora(entity: Encordadora): Encordadora {
        logger.info { "Creando máquina encordadora $entity" }
        return maquinaEncordadoraRepository.save(entity)
    }

    suspend fun getEncordadoraById(id: ObjectId): Encordadora? {
        logger.info { "Obteniendo máquina encordadora con id $id" }
        return maquinaEncordadoraRepository.findById(id)
    }

    suspend fun deleteEncordadora(entity: Encordadora) {
        logger.info { "Borrando máquina encordadora $entity" }
        maquinaEncordadoraRepository.delete(entity)
    }

    suspend fun resetEncordadoras() {
        logger.info { "Borrando máquinas encordadoras" }
        maquinaEncordadoraRepository.deleteAll()
    }
}