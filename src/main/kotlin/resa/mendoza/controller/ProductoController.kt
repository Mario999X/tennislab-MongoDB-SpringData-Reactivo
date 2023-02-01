package resa.mendoza.controller

import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ChangeStreamEvent
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

    suspend fun getProductoById(id: ObjectId): Producto? {
        logger.info { "Obteniendo producto con id $id" }
        return productosRepository.findById(id)
    }

    suspend fun deleteProducto(entity: Producto) {
        logger.info { "Borrando producto $entity" }
        productosRepository.delete(entity)
    }

    suspend fun resetProductos() {
        logger.info { "Borrando productos" }
        productosRepository.deleteAll()
    }

    fun watchProductos(): Flow<ChangeStreamEvent<Producto>> {
        logger.info { "Watch productos" }
        return productoService.watch()
    }
}