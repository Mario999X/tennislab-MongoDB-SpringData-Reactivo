package resa.mendoza.controller

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
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
import resa.mendoza.dto.toUsuarioDto
import resa.mendoza.models.Perfil
import resa.mendoza.models.Usuario
import resa.mendoza.repositories.UsuariosCacheRepository
import resa.mendoza.repositories.UsuariosKtorFitRepository
import resa.mendoza.repositories.UsuariosMongoRepository
import resa.mendoza.utils.Cifrador

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
internal class UsuarioControllerTest {

    @MockK
    private lateinit var usuariosMongoRepository: UsuariosMongoRepository

    @MockK
    private lateinit var usuariosKtorFitRepository: UsuariosKtorFitRepository

    @MockK
    private lateinit var usuariosCacheRepository: UsuariosCacheRepository

    @InjectMockKs
    private lateinit var usuarioController: UsuarioController

    private val usuario = Usuario(
        name = "Data1",
        email = "Data2@Data3.com",
        password = Cifrador.codifyPassword("Data4"),
        perfil = Perfil.ADMIN
    )

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllUsuariosApi() = runTest {
        coEvery { usuariosKtorFitRepository.findAll() } returns flowOf(usuario.toUsuarioDto())
        val res = usuarioController.getAllUsuariosApi().toList()

        assertAll(
            { assertEquals(1, res.size) }
        )
        coVerify(exactly = 1) { usuariosKtorFitRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllUsuariosMongo() = runTest {
        coEvery { usuariosMongoRepository.findAll() } returns flowOf(usuario)
        val res = usuarioController.getAllUsuariosMongo().toList()

        assertAll(
            { assertEquals(1, res.size) }
        )
        coVerify(exactly = 1) { usuariosMongoRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllUsuariosCache() = runTest {
        coEvery { usuariosCacheRepository.findAll() } returns flowOf(usuario)
        val res = usuarioController.getAllUsuariosCache().toList()

        assertAll(
            { assertEquals(1, res.size) }
        )
        coVerify(exactly = 1) { usuariosCacheRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createUsuario() = runTest {
        coEvery { usuariosMongoRepository.save(usuario) } returns usuario
        coEvery { usuariosCacheRepository.save(usuario) } returns usuario

        val res = usuarioController.createUsuario(usuario)

        assertAll(
            { assertEquals(res.name, usuario.name) }
        )

        coVerify(exactly = 1) { usuariosMongoRepository.save(usuario) }
        coVerify(exactly = 1) { usuariosCacheRepository.save(usuario) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getUsuarioById() = runTest {
        coEvery { usuariosCacheRepository.findById(any()) } returns usuario

        val res = usuarioController.getUsuarioById(usuario.id)

        assertAll(
            { assertEquals(res!!.name, usuario.name) }
        )
        coVerify(exactly = 1) { usuariosCacheRepository.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteUsuario() = runTest {
        coEvery { usuariosMongoRepository.delete(usuario) } returns Unit
        coEvery { usuariosCacheRepository.delete(usuario) } returns true

        usuarioController.deleteUsuario(usuario)

        coVerify(exactly = 1) { usuariosMongoRepository.delete(usuario) }
        coVerify(exactly = 1) { usuariosCacheRepository.delete(usuario) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun resetUsuariosMongo() = runTest {
        coEvery { usuariosMongoRepository.deleteAll() } returns Unit

        val res = usuarioController.resetUsuariosMongo()

        assertEquals(res, Unit)
        coVerify(exactly = 1) { usuariosMongoRepository.deleteAll() }
    }
}