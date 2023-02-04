package resa.mendoza.repositories

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Repository
import resa.mendoza.dto.UsuarioDto
import resa.mendoza.ktorfit.KtorFitClient

/**
 * Repositorio de Usuarios en la API
 */
@Repository
class UsuariosKtorFitRepository {

    private val client by lazy { KtorFitClient.instance }

    suspend fun findAll(): Flow<UsuarioDto> = withContext(Dispatchers.IO) {
        val call = client.getAll()

        return@withContext call.asFlow()
    }
}