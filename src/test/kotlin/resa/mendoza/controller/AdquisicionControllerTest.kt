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
import resa.mendoza.models.Adquisicion
import resa.mendoza.models.Producto
import resa.mendoza.models.Tipo
import resa.mendoza.repositories.AdquisicionRepository

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
internal class AdquisicionControllerTest {

    @MockK
    private lateinit var adquisicionRepository: AdquisicionRepository

    @InjectMockKs
    private lateinit var adquisicionController: AdquisicionController

    private val adquisicion = Adquisicion(
        cantidad = 2,
        producto = Producto(
            tipo = Tipo.RAQUETA,
            descripcion = "Babolat Pure Air",
            stock = 3,
            precio = 279.95
        ),
    )

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAdquisiciones() = runTest {
        every { adquisicionRepository.findAll() } returns flowOf(adquisicion)
        val res = adquisicionController.getAdquisiciones().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        verify(exactly = 1) { adquisicionRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createAdquisicion() = runTest {
        coEvery { adquisicionRepository.save(any()) } returns adquisicion
        val res = adquisicionController.createAdquisicion(adquisicion)
        assertAll(
            { assertEquals(res.precio, adquisicion.precio) }
        )

        coVerify(exactly = 1) { adquisicionRepository.save(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAdquisicionById() = runTest {
        coEvery { adquisicionRepository.findById(any()) } returns adquisicion
        val res = adquisicionController.getAdquisicionById(adquisicion.id)
        assertAll(
            { assertEquals(res!!.precio, adquisicion.precio) }
        )

        coVerify { adquisicionRepository.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNotExists() = runTest {
        coEvery { adquisicionRepository.findById(any()) } returns null
        val res = adquisicionController.getAdquisicionById(ObjectId.get())

        assertNull(res)

        coVerify { adquisicionRepository.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteAdquisicion() = runTest {
        coEvery { adquisicionRepository.delete(adquisicion) } returns Unit

        val res = adquisicionController.deleteAdquisicion(adquisicion)

        assertEquals(res, Unit)
        coVerify(exactly = 1) { adquisicionRepository.delete(adquisicion) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun resetAdquisiciones() = runTest {
        coEvery { adquisicionRepository.deleteAll() } returns Unit

        val res = adquisicionController.resetAdquisiciones()

        assertEquals(res, Unit)
        coVerify { adquisicionRepository.deleteAll() }
    }
}