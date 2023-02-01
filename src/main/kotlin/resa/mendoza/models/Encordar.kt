package resa.mendoza.models

import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("encordaciones")
data class Encordar(
    @Id
    val id: ObjectId = ObjectId.get(),
    val uuid: UUID = UUID.randomUUID(),
    var informacionEndordado: String,
    val precio: Double = 15.0
) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}
