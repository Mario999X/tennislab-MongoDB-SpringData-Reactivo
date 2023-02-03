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
import resa.mendoza.models.maquina.Personalizadora
import resa.mendoza.repositories.MaquinaPersonalizadoraRepository
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
internal class MaquinaPersonalizadoraControllerTest {

    @MockK
    private lateinit var maquinaPersonalizadoraRepository: MaquinaPersonalizadoraRepository

    @InjectMockKs
    private lateinit var maquinaPersonalizadoraController: MaquinaPersonalizadoraController

    private val personalizadora = Personalizadora(
        descripcion = "Toshiba ABC",
        fechaAdquisicion = LocalDate.now().toString(),
        numSerie = 540L,
        maniobrabilidad = true,
        balance = false,
        rigidez = false
    )

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPersonalizadoras() = runTest {
        every { maquinaPersonalizadoraRepository.findAll() } returns flowOf(personalizadora)
        val res = maquinaPersonalizadoraController.getPersonalizadoras().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        verify(exactly = 1) { maquinaPersonalizadoraRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createPersonalizadora() = runTest {
        coEvery { maquinaPersonalizadoraRepository.save(any()) } returns personalizadora
        val res = maquinaPersonalizadoraController.createPersonalizadora(personalizadora)
        assertAll(
            { assertEquals(res.balance, personalizadora.balance) }
        )
        coVerify(exactly = 1) { maquinaPersonalizadoraRepository.save(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPersonalizadoraById() = runTest {
        coEvery { maquinaPersonalizadoraRepository.findById(any()) } returns personalizadora
        val res = maquinaPersonalizadoraController.getPersonalizadoraById(personalizadora.id)
        assertAll(
            { assertEquals(res!!.balance, personalizadora.balance) }
        )
        coVerify(exactly = 1) { maquinaPersonalizadoraRepository.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deletePersonalizadora() = runTest {
        coEvery { maquinaPersonalizadoraRepository.delete(any()) } returns Unit

        val res = maquinaPersonalizadoraController.deletePersonalizadora(personalizadora)

        assertEquals(res, Unit)
        coVerify(exactly = 1) { maquinaPersonalizadoraRepository.delete(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun resetPersonalizadoras() = runTest {
        coEvery { maquinaPersonalizadoraRepository.deleteAll() } returns Unit

        val res = maquinaPersonalizadoraController.resetPersonalizadoras()

        assertEquals(res, Unit)
        coVerify(exactly = 1) { maquinaPersonalizadoraRepository.deleteAll() }
    }
}