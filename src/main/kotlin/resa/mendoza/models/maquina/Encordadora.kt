package resa.mendoza.models.maquina

import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import resa.mendoza.models.Turno
import java.util.*

@Document("encordadoras")
class Encordadora(
    id: ObjectId = ObjectId.get(),
    uuid: UUID = UUID.randomUUID(),
    descripcion: String,
    fechaAdquisicion: String,
    numSerie: Long,
    turno: Turno? = null,
    var isManual: Boolean,
    var tensionMax: Double,
    var tensionMin: Double,
) : Maquina(id, uuid, descripcion, fechaAdquisicion, numSerie, turno) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}