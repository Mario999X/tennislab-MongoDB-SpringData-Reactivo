package resa.mendoza.repositories

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.stereotype.Repository
import resa.mendoza.dto.toTareaDto
import resa.mendoza.ktorfit.KtorFitClient
import resa.mendoza.models.Tarea


private val logger = KotlinLogging.logger { }

@Repository
class TareasKtorFitRepository {

    private val client by lazy { KtorFitClient.instance }

    suspend fun uploadTarea(entity: Tarea): Tarea = withContext(Dispatchers.IO) {
        logger.info { "Subiendo tarea $entity al historico" }

        client.createTarea(entity.toTareaDto())
        return@withContext entity
    }
}