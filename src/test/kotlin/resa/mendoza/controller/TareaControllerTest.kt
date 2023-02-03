package resa.mendoza.controller

import io.mockk.coEvery
import io.mockk.coVerify
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
import resa.mendoza.db.getAdquisicionInit
import resa.mendoza.db.getPersonalizaciones
import resa.mendoza.models.Perfil
import resa.mendoza.models.Tarea
import resa.mendoza.models.Usuario
import resa.mendoza.repositories.TareaRepository
import resa.mendoza.repositories.TareasKtorFitRepository
import resa.mendoza.utils.Cifrador

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class TareaControllerTest {
    private val tarea = Tarea(
        adquisicion = getAdquisicionInit()[1],
        personalizar = getPersonalizaciones()[1],
        usuario = Usuario(
            ObjectId.get(),
            name = "Data1",
            email = "Data2@Data3.com",
            password = Cifrador.codifyPassword("Data4"),
            perfil = Perfil.ENCORDADOR
        )
    )

    @MockK
    private lateinit var tareaRepository: TareaRepository

    @MockK
    private lateinit var tareasKtorFitRepository: TareasKtorFitRepository

    @InjectMockKs
    private lateinit var tareaController: TareaController

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTareas() = runTest {
        coEvery { tareaRepository.findAll() } returns flowOf(tarea)
        val res = tareaController.getTareas().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        coVerify(exactly = 1) { tareaRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createTarea() = runTest {
        coEvery { tareaRepository.save(any()) } returns tarea
        coEvery { tareasKtorFitRepository.uploadTarea(any()) } returns tarea
        val res = tareaController.createTarea(tarea)
        assertAll(
            { assertEquals(res.usuario, tarea.usuario) }
        )

        coVerify(exactly = 1) { tareaRepository.save(any()) }
        coVerify(exactly = 1) { tareasKtorFitRepository.uploadTarea(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTareaById() = runTest {
        coEvery { tareaRepository.findById(tarea.id) } returns tarea
        val res = tareaController.getTareaById(tarea.id)
        assertAll(
            { assertEquals(res!!.usuario, tarea.usuario) }
        )
        coVerify(exactly = 1) { tareaRepository.findById(tarea.id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteTarea() = runTest {
        coEvery { tareaRepository.delete(tarea) } returns Unit
        val res = tareaController.deleteTarea(tarea)
        assertEquals(res, Unit)
        coVerify(exactly = 1) { tareaRepository.delete(tarea) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun resetTarea() = runTest {
        coEvery { tareaRepository.deleteAll() } returns Unit
        val res = tareaController.resetTarea()
        assertEquals(res, Unit)
        coVerify(exactly = 1) { tareaRepository.deleteAll() }
    }
}