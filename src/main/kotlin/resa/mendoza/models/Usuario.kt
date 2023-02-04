package resa.mendoza.models

import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("usuarios")
class Usuario(
    @Id
    val id: ObjectId = ObjectId.get(),
    val uuid: UUID = UUID.randomUUID(),
    var name: String,
    var email: String,
    val password: ByteArray,
    var raqueta: List<Raqueta>? = null,
    var perfil: Perfil
) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}

enum class Perfil { ADMIN, ENCORDADOR, CLIENTE }