package resa.mendoza.dto

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import resa.mendoza.models.Perfil
import resa.mendoza.models.Usuario
import resa.mendoza.utils.Cifrador
import java.util.*

@Serializable
data class UsuarioDto(
    val id: String,
    var name: String,
    var email: String
)

fun UsuarioDto.toUsuario(pass: String): Usuario {
    return Usuario(
        id = ObjectId.get(),
        uuid = UUID.randomUUID(),
        name,
        email,
        password = Cifrador.codifyPassword(pass),
        perfil = Perfil.CLIENTE
    )
}

fun Usuario.toUsuarioDto(): UsuarioDto {
    return UsuarioDto(
        id = id.toString(),
        name, email
    )
}
