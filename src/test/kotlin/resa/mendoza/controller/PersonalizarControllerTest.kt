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
import resa.mendoza.models.Personalizar
import resa.mendoza.repositories.PersonalizarRepository

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class PersonalizarControllerTest {
    private val personalizar = Personalizar(
        informacionPersonalizacion = "Dato1"
    )

    @MockK
    private lateinit var personalizarRepository: PersonalizarRepository

    @InjectMockKs
    private lateinit var personalizarController: PersonalizarController

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPersonalizaciones() = runTest {
        every { personalizarRepository.findAll() } returns flowOf(personalizar)
        val res = personalizarController.getPersonalizaciones().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        verify(exactly = 1) { personalizarRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createPersonalizaciones() = runTest {
        coEvery { personalizarRepository.save(any()) } returns personalizar
        val res = personalizarController.createPersonalizaciones(personalizar)
        assertAll(
            { assertEquals(res.precio, personalizar.precio) }
        )

        coVerify(exactly = 1) { personalizarRepository.save(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPersonalizacionById() = runTest {
        coEvery { personalizarRepository.findById(personalizar.id) } returns personalizar
        val res = personalizarController.getPersonalizacionById(personalizar.id)
        assertAll(
            { assertEquals(res!!.precio, personalizar.precio) }
        )

        coVerify(exactly = 1) { personalizarRepository.findById(personalizar.id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNotExists() = runTest {
        coEvery { personalizarRepository.findById(any()) } returns null
        val res = personalizarController.getPersonalizacionById(ObjectId.get())

        assertNull(res)

        coVerify { personalizarRepository.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deletePersonalizacion() = runTest {
        coEvery { personalizarRepository.delete(personalizar) } returns Unit
        val res = personalizarController.deletePersonalizacion(personalizar)
        assertEquals(res, Unit)
        coVerify(exactly = 1) { personalizarRepository.delete(personalizar) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun resetPersonalizaciones() = runTest {
        coEvery { personalizarRepository.deleteAll() } returns Unit
        val res = personalizarController.resetPersonalizaciones()
        assertEquals(res, Unit)
        coVerify(exactly = 1) { personalizarRepository.deleteAll() }
    }
}