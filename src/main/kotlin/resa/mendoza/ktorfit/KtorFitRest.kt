package resa.mendoza.ktorfit

/**
 *  @author Mario Resa y Sebastián Mendoza
 */
import de.jensklingenberg.ktorfit.http.GET
import resa.mendoza.dto.UsuarioDto

/**
 * Interfaz en la que se definen los endpoints de la API a consultar
 */
interface KtorFitRest {

    @GET("users")
    suspend fun getAll(): List<UsuarioDto>

}