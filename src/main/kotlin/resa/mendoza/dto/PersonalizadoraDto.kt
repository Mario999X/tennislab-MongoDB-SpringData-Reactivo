package resa.mendoza.dto

import kotlinx.serialization.Serializable
import resa.mendoza.models.maquina.Personalizadora

@Serializable
data class PersonalizadoraDto(
    val descripcion: String,
    val fechaAdquisicion: String,
    val numSerie: String,
    var maniobrabilidad: String,
    var balance: String,
    var rigidez: String
) {
}

fun Personalizadora.toPersonalizadoraDto(): PersonalizadoraDto {
    return PersonalizadoraDto(
        descripcion,
        fechaAdquisicion,
        numSerie.toString(),
        maniobrabilidad.toString(),
        balance.toString(),
        rigidez.toString()
    )
}
