package resa.mendoza.controller

import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import resa.mendoza.models.Pedido
import resa.mendoza.repositories.PedidoRepository

private val logger = KotlinLogging.logger { }

@Controller
class PedidoController
@Autowired constructor(
    private val pedidoRepository: PedidoRepository
) {
    fun getPedidos(): Flow<Pedido> {
        logger.info { "Obteniendo pedidos" }
        return pedidoRepository.findAll()
    }

    suspend fun createPedido(entity: Pedido): Pedido {
        logger.info { "Creando pedido $entity" }
        return pedidoRepository.save(entity)
    }

    suspend fun getPedidoById(id: ObjectId): Pedido? {
        logger.info { "Obteniendo pedido con id $id" }
        return pedidoRepository.findById(id)
    }

    suspend fun deletePedido(entity: Pedido) {
        logger.info { "Borrando pedido $entity" }
        pedidoRepository.delete(entity)
    }

    suspend fun resetPedidos() {
        logger.info { "Borrando pedidos" }
        pedidoRepository.deleteAll()
    }
}