package resa.mendoza.dto

import kotlinx.serialization.Serializable
import resa.mendoza.models.Tarea

@Serializable
data class TareaDto(
    val adquisicionDto: AdquisicionDto?,
    val personalizarDto: PersonalizarDto?,
    val encordarDto: EncordarDto?,
    val precio: String,
    val usuarioDto: UsuarioDto
) {

}

fun Tarea.toTareaDto(): TareaDto {
    return TareaDto(
        adquisicionDto = adquisicion?.toAdquisicionDto(),
        personalizarDto = personalizar?.toPersonalizarDto(),
        encordarDto = encordar?.toEncordarDto(),
        precio = precio.toString(),
        usuarioDto = usuario.toUsuarioDto()
    )
}