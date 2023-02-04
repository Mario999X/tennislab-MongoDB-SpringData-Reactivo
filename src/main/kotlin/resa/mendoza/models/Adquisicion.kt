package resa.mendoza.models

import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import resa.mendoza.utils.CalculoPrecioTarea
import java.util.*

@Document("adquisiciones")
data class Adquisicion(
    @Id
    val id: ObjectId = ObjectId.get(),
    val uuid: UUID = UUID.randomUUID(),
    var cantidad: Int,
    val producto: Producto,
    var precio: Double = CalculoPrecioTarea.calculatePrecio(producto.precio, null, null) * cantidad
) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}