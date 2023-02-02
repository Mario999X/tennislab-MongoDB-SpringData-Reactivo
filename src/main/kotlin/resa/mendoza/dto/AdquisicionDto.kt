package resa.mendoza.dto

import kotlinx.serialization.Serializable
import resa.mendoza.models.Adquisicion

@Serializable
data class AdquisicionDto(
    var cantidad: String,
    var productoDto: ProductoDto,
    var precio: String
) {
}

fun Adquisicion.toAdquisicionDto(): AdquisicionDto {
    return AdquisicionDto(
        cantidad = cantidad.toString(),
        productoDto = producto.toProductoDto(),
        precio = precio.toString()
    )
}
