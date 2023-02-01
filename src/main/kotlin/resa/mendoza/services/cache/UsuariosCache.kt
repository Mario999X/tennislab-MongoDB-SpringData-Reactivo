package resa.mendoza.services.cache

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import io.github.reactivecircus.cache4k.Cache
import org.bson.types.ObjectId
import resa.mendoza.models.Usuario
import kotlin.time.Duration.Companion.minutes

/**
 * Clase donde se configura las características de la caché
 */
class UsuariosCache {

    val refreshTime = 60000 // 1 minuto

    val cache = Cache.Builder()
        .expireAfterAccess(5.minutes)
        .build<ObjectId, Usuario>()
}