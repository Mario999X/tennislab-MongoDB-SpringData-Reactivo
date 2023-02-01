package resa.mendoza.models

import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("turnos")
data class Turno(
    @Id
    val id: ObjectId = ObjectId.get(),
    val uuid: UUID = UUID.randomUUID(),
    var horario: TipoHorario,
    var trabajador: Usuario
) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}

enum class TipoHorario { TEMPRANO, TARDE }