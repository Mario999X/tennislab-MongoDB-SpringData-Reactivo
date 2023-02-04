package resa.mendoza.utils

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import com.toxicbakery.bcrypt.Bcrypt

/**
 * Calse Object que contiene una función que encripta Strings con BCrypt
 */
object Cifrador {
    fun codifyPassword(password: String): ByteArray {
        return Bcrypt.hash(password, 12)
    }
}