package resa.mendoza.dto

import kotlinx.serialization.Serializable
import resa.mendoza.models.Personalizar

@Serializable
data class PersonalizarDto(
    val informacionPersonalizacion: String,
    val precio: String
) {

}

fun Personalizar.toPersonalizarDto(): PersonalizarDto {
    return PersonalizarDto(
        informacionPersonalizacion,
        precio = precio.toString()
    )
}
