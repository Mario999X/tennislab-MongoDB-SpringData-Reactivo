package resa.mendoza.models

import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import resa.mendoza.utils.CalculoPrecioTarea
import java.util.UUID

@Document("tareas")
data class Tarea(
    val id: ObjectId = ObjectId.get(),
    val uuid: UUID = UUID.randomUUID(),
    val adquisicion: Adquisicion? = null,
    val personalizar: Personalizar? = null,
    val encordar: Encordar? = null,
    var precio: Double = CalculoPrecioTarea.calculatePrecio(
        adquisicion?.precio,
        personalizar?.precio,
        encordar?.precio
    ),
    var usuario: Usuario,
    var raqueta: Raqueta? = null
) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}