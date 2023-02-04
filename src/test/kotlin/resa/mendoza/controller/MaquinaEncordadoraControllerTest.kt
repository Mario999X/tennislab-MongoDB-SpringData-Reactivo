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
import resa.mendoza.models.maquina.Encordadora
import resa.mendoza.repositories.MaquinaEncordadoraRepository
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
internal class MaquinaEncordadoraControllerTest {

    @MockK
    private lateinit var maquinaEncordadoraRepository: MaquinaEncordadoraRepository

    @InjectMockKs
    private lateinit var maquinaEncordadoraController: MaquinaEncordadoraController

    private val encordadora = Encordadora(
        descripcion = "Vevor",
        fechaAdquisicion = LocalDate.now().toString(),
        numSerie = 320L,
        isManual = true,
        tensionMax = 20.2,
        tensionMin = 17.5
    )

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEncordadoras() = runTest {
        every { maquinaEncordadoraRepository.findAll() } returns flowOf(encordadora)
        val res = maquinaEncordadoraController.getEncordadoras().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        verify(exactly = 1) { maquinaEncordadoraRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createEncordadora() = runTest {
        coEvery { maquinaEncordadoraRepository.save(any()) } returns encordadora
        val res = maquinaEncordadoraController.createEncordadora(encordadora)
        assertAll(
            { assertEquals(res.isManual, encordadora.isManual) }
        )

        coVerify(exactly = 1) { maquinaEncordadoraRepository.save(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEncordadoraById() = runTest {
        coEvery { maquinaEncordadoraRepository.findById(any()) } returns encordadora
        val res = maquinaEncordadoraController.getEncordadoraById(encordadora.id)
        assertAll(
            { assertEquals(res!!.isManual, encordadora.isManual) }
        )

        coVerify { maquinaEncordadoraRepository.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNotExists() = runTest {
        coEvery { maquinaEncordadoraRepository.findById(any()) } returns null
        val res = maquinaEncordadoraController.getEncordadoraById(ObjectId.get())

        assertNull(res)

        coVerify { maquinaEncordadoraRepository.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteEncordadora() = runTest {
        coEvery { maquinaEncordadoraRepository.delete(encordadora) } returns Unit

        val res = maquinaEncordadoraController.deleteEncordadora(encordadora)

        assertEquals(res, Unit)
        coVerify(exactly = 1) { maquinaEncordadoraRepository.delete(encordadora) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun resetEncordadoras() = runTest {
        coEvery { maquinaEncordadoraRepository.deleteAll() } returns Unit

        val res = maquinaEncordadoraController.resetEncordadoras()

        assertEquals(res, Unit)
        coVerify(exactly = 1) { maquinaEncordadoraRepository.deleteAll() }
    }
}