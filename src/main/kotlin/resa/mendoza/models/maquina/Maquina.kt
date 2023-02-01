package resa.mendoza.models.maquina

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import resa.mendoza.models.Turno
import java.util.*

open class Maquina(
    @Id
    val id: ObjectId = ObjectId.get(),
    val uuid: UUID = UUID.randomUUID(),
    val descripcion: String,
    var fechaAdquisicion: String,
    var numSerie: Long,
    var turno: Turno?
) {
    override fun toString(): String {
        return "Maquina(id=$id, uuid=$uuid, descripcion='$descripcion', fechaAdquisicion='$fechaAdquisicion', numSerie=$numSerie, turno=$turno)"
    }
}