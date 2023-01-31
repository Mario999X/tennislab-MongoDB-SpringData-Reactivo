package resa.mendoza.controller

import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import resa.mendoza.models.Producto
import resa.mendoza.repositories.ProductosRepository
import resa.mendoza.services.ProductoService

private val logger = KotlinLogging.logger { }

@Controller
class ProductoController
@Autowired constructor(
    private val productosRepository: ProductosRepository,
    private val productoService: ProductoService
) {
    fun getProductos(): Flow<Producto> {
        logger.info { "Obteniendo productos" }
        return productosRepository.findAll()
    }

    suspend fun createProducto(item: Producto): Producto {
        logger.info { "Creando producto $item" }
        productosRepository.save(item)
        return item
    }

    suspend fun productosDeleteAll() {
        logger.info { "Borrando productos" }
        productosRepository.deleteAll()
    }
}