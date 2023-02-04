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
import org.springframework.stereotype.Controller
import resa.mendoza.dto.toPedidoDto
import resa.mendoza.models.Pedido
import resa.mendoza.models.ResponseFailure
import resa.mendoza.models.ResponseSuccess
import resa.mendoza.repositories.PedidoRepository

private val logger = KotlinLogging.logger { }

/**
 * Controlador al que se le pasa un repositorio, el cual contiene el CRUD para los pedidos
 * @param pedidoRepository
 */
@Controller
class PedidoController
@Autowired constructor(
    private val pedidoRepository: PedidoRepository
) {
    fun getPedidos(): Flow<Pedido> {
        logger.info { "Obteniendo pedidos" }
        val response = pedidoRepository.findAll()

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    suspend fun createPedido(entity: Pedido): Pedido {
        logger.info { "Creando pedido $entity" }
        val response = pedidoRepository.save(entity)

        println(Json.encodeToString(ResponseSuccess(201, response.toPedidoDto())))
        return response
    }

    suspend fun getPedidoById(id: ObjectId): Pedido? {
        logger.info { "Obteniendo pedido con id $id" }
        val response = pedidoRepository.findById(id)

        if (response == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Pedido not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, response.toPedidoDto())))
        return response
    }

    suspend fun deletePedido(entity: Pedido) {
        logger.info { "Borrando pedido $entity" }

        println(Json.encodeToString(ResponseSuccess(200, entity.toPedidoDto())))
        pedidoRepository.delete(entity)
    }

    suspend fun resetPedidos() {
        logger.info { "Borrando pedidos" }
        pedidoRepository.deleteAll()
    }
}