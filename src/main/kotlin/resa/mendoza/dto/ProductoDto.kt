package resa.mendoza.dto

import kotlinx.serialization.Serializable
import resa.mendoza.models.Producto

@Serializable
data class ProductoDto(
    val tipo: String,
    val descripcion: String,
    val stock: String,
    val precio: String
) {
}

fun Producto.toProductoDto(): ProductoDto {
    return ProductoDto(
        tipo = tipo.name,
        descripcion,
        stock = stock.toString(),
        precio = precio.toString()
    )
}
