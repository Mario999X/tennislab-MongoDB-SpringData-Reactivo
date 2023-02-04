package resa.mendoza.controller

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ChangeStreamEvent
import org.springframework.stereotype.Controller
import resa.mendoza.dto.toProductoDto
import resa.mendoza.models.Producto
import resa.mendoza.models.ResponseFailure
import resa.mendoza.models.ResponseSuccess
import resa.mendoza.repositories.ProductosRepository
import resa.mendoza.services.ProductoService

private val logger = KotlinLogging.logger { }

/**
 * Controlador al que se le pasa un repositorio y un servicio, el cual contiene el CRUD para los productos
 * @param productosRepository
 * @param productoService
 */
@Controller
class ProductoController
@Autowired constructor(
    private val productosRepository: ProductosRepository,
    private val productoService: ProductoService
) {
    fun getProductos(): Flow<Producto> {
        logger.info { "Obteniendo productos" }
        val response = productosRepository.findAll()

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    suspend fun createProducto(item: Producto): Producto {
        logger.info { "Creando producto $item" }
        val response = productosRepository.save(item)

        println(Json.encodeToString(ResponseSuccess(201, item.toProductoDto())))
        return response
    }

    suspend fun getProductoById(id: ObjectId): Producto? {
        logger.info { "Obteniendo producto con id $id" }
        val response = productosRepository.findById(id)

        if (response == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Producto not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, response.toProductoDto())))

        return response
    }

    suspend fun deleteProducto(entity: Producto) {
        logger.info { "Borrando producto $entity" }

        println(Json.encodeToString(ResponseSuccess(200, entity.toProductoDto())))
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