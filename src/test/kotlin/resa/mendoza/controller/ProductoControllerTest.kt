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
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import resa.mendoza.models.Producto
import resa.mendoza.models.Tipo
import resa.mendoza.repositories.ProductosRepository
import resa.mendoza.services.ProductoService

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
internal class ProductoControllerTest {
    private val producto = Producto(
        tipo = Tipo.COMPLEMENTO,
        descripcion = "Wilson Dazzle",
        stock = 5,
        precio = 7.90
    )

    @MockK
    private lateinit var productosRepository: ProductosRepository

    @MockK
    private lateinit var productoService: ProductoService

    @InjectMockKs
    private lateinit var productoController: ProductoController

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProductos() = runTest {
        every { productosRepository.findAll() } returns flowOf(producto)
        val res = productoController.getProductos().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        verify(exactly = 1) { productosRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createProducto() = runTest {
        coEvery { productosRepository.save(any()) } returns producto
        val res = productoController.createProducto(producto)
        assertAll(
            { assertEquals(res.descripcion, producto.descripcion) }
        )

        coVerify(exactly = 1) { productosRepository.save(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProductoById() = runTest {
        coEvery { productosRepository.findById(producto.id) } returns producto
        val res = productoController.getProductoById(producto.id)
        assertAll(
            { assertEquals(res!!.descripcion, producto.descripcion) }
        )

        coVerify { productosRepository.findById(producto.id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNotExists() = runTest {
        coEvery { productosRepository.findById(any()) } returns null
        val res = productoController.getProductoById(ObjectId.get())

        assertNull(res)

        coVerify { productosRepository.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteProducto() = runTest {
        coEvery { productosRepository.delete(producto) } returns Unit

        val res = productoController.deleteProducto(producto)
        assertEquals(res, Unit)
        coVerify(exactly = 1) { productosRepository.delete(producto) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun resetProductos() = runTest {
        coEvery { productosRepository.deleteAll() } returns Unit
        val res = productoController.resetProductos()
        assertEquals(res, Unit)
        coVerify(exactly = 1) { productosRepository.deleteAll() }
    }

}