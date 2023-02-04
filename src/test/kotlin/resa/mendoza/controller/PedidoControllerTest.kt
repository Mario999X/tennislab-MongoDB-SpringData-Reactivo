package resa.mendoza.controller

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import resa.mendoza.models.EstadoPedido
import resa.mendoza.models.Pedido
import resa.mendoza.models.Perfil
import resa.mendoza.models.Usuario
import resa.mendoza.repositories.PedidoRepository
import resa.mendoza.utils.Cifrador
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
internal class PedidoControllerTest {
    private val pedido = Pedido(
        estadoPedido = EstadoPedido.PROCESANDO,
        fechaEntrada = LocalDateTime.now().toString(),
        fechaProgramada = LocalDateTime.now().plusDays(10).toString(),
        cliente = Usuario(
            name = "Data1",
            email = "Data2@Data3.com",
            password = Cifrador.codifyPassword("Data4"),
            perfil = Perfil.CLIENTE
        ),
        tareas = listOf()
    )

    @MockK
    private lateinit var pedidoRepository: PedidoRepository

    @InjectMockKs
    private lateinit var pedidoController: PedidoController

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPedidos() = runTest {
        every { pedidoRepository.findAll() } returns flowOf(pedido)
        val res = pedidoController.getPedidos().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        verify(exactly = 1) { pedidoRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createPedido() = runTest {
        coEvery { pedidoRepository.save(any()) } returns pedido
        val res = pedidoController.createPedido(pedido)
        assertAll(
            { assertEquals(res.precio, pedido.precio) }
        )

        coVerify(exactly = 1) { pedidoRepository.save(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPedidoById() = runTest {
        coEvery { pedidoRepository.findById(pedido.id) } returns pedido
        val res = pedidoController.getPedidoById(pedido.id)
        assertAll(
            { assertEquals(res!!.precio, pedido.precio) }
        )

        coVerify { pedidoRepository.findById(pedido.id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNotExists() = runTest {
        coEvery { pedidoRepository.findById(any()) } returns null
        val res = pedidoController.getPedidoById(ObjectId.get())

        assertNull(res)

        coVerify { pedidoRepository.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deletePedido() = runTest {
        coEvery { pedidoRepository.delete(pedido) } returns Unit
        val res = pedidoController.deletePedido(pedido)
        assertEquals(res, Unit)
        coVerify(exactly = 1) { pedidoRepository.delete(pedido) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun resetPedidos() = runTest {
        coEvery { pedidoRepository.deleteAll() } returns Unit
        val res = pedidoController.resetPedidos()
        assertEquals(res, Unit)
        coVerify(exactly = 1) { pedidoRepository.deleteAll() }
    }
}