package resa.mendoza.ktorfit

/**
 *  @author Mario Resa y Sebastián Mendoza
 */
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import resa.mendoza.dto.TareaDto
import resa.mendoza.dto.UsuarioDto

/**
 * Interfaz en la que se definen los endpoints de la API a consultar
 */
interface KtorFitRest {

    @GET("users")
    suspend fun getAll(): List<UsuarioDto>

    @POST("todos")
    suspend fun createTarea(@Body tarea: TareaDto): TareaDto
}