package resa.mendoza.controller

import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import resa.mendoza.models.Encordar
import resa.mendoza.repositories.EncordarRepository

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
internal class EncordarControllerTest {

    @MockK
    private lateinit var encordarRepository: EncordarRepository

    @InjectMockKs
    private lateinit var encordarController: EncordarController

    private val encordar = Encordar(
        informacionEndordado = "Dato1"
    )

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEncordaciones() = runTest {
        every { encordarRepository.findAll() } returns flowOf(encordar)
        val res = encordarController.getEncordaciones().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        verify(exactly = 1) { encordarRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createEncordar() = runTest {
        coEvery { encordarRepository.save(any()) } returns encordar
        val res = encordarController.createEncordar(encordar)
        assertAll(
            { assertEquals(res.informacionEndordado, encordar.informacionEndordado) }
        )

        coVerify(exactly = 1) { encordarRepository.save(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEncordarById() = runTest {
        coEvery { encordarRepository.findById(any()) } returns encordar
        val res = encordarController.getEncordarById(encordar.id)
        assertAll(
            { assertEquals(res!!.precio, encordar.precio) }
        )

        coVerify(exactly = 1) { encordarRepository.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteEncordacion() = runTest {
        coEvery { encordarRepository.delete(encordar) } returns Unit

        val res = encordarController.deleteEncordacion(encordar)

        assertEquals(res, Unit)
        coVerify(exactly = 1) { encordarRepository.delete(encordar) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun resetEncordaciones() = runTest {
        coEvery { encordarRepository.deleteAll() } returns Unit

        val res = encordarController.resetEncordaciones()

        assertEquals(res, Unit)
        coVerify(exactly = 1) { encordarRepository.deleteAll() }
    }
}