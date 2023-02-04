package resa.mendoza.dto

import kotlinx.serialization.Serializable
import resa.mendoza.models.maquina.Encordadora

@Serializable
data class EncordadoraDto(
    val descripcion: String,
    val fechaAdquisicion: String,
    val numSerie: String,
    var isManual: String,
    var tensionMax: String,
    var tensionMin: String,
)

fun Encordadora.toEncordadoraDto(): EncordadoraDto {
    return EncordadoraDto(
        descripcion,
        fechaAdquisicion,
        numSerie.toString(),
        isManual.toString(),
        tensionMax.toString(),
        tensionMin.toString()
    )
}
