package resa.mendoza.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("productos")
data class Producto(
    @Id
    val id: ObjectId = ObjectId.get(),
    val uuid: UUID = UUID.randomUUID(),
    val tipo: Tipo,
    val descripcion: String,
    val stock: Int,
    var precio: Double
){
    override fun toString(): String {
        return "Producto(id=$id, uuid=$uuid, tipo=$tipo, descripcion='$descripcion', stock=$stock, precio=$precio)"
    }
}
enum class Tipo { RAQUETA, CORDAJE, COMPLEMENTO }