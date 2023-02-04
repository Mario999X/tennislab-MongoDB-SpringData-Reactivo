package resa.mendoza.models

import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Pedido(
    @Id
    val id: ObjectId = ObjectId.get(),
    val uuid: UUID = UUID.randomUUID(),
    var estadoPedido: EstadoPedido,
    val fechaEntrada: String,
    val fechaProgramada: String,
    var fechaSalida: String? = null,
    var cliente: Usuario,
    var tareas: List<Tarea>,
    val precio: Double = tareas.sumOf { it.precio }
) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}

enum class EstadoPedido { RECIBIDO, PROCESANDO, TERMINADO }