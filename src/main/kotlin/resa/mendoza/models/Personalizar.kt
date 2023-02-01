package resa.mendoza.models

import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("personalizaciones")
data class Personalizar(
    @Id
    val id: ObjectId = ObjectId.get(),
    val uuid: UUID = UUID.randomUUID(),
    var informacionPersonalizacion: String,
    val precio: Double = 60.0
) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}
