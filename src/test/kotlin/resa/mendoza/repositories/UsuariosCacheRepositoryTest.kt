package resa.mendoza.repositories

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import resa.mendoza.models.Perfil
import resa.mendoza.models.Usuario
import resa.mendoza.utils.Cifrador

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class UsuariosCacheRepositoryTest {

    private val usuario = Usuario(
        id = ObjectId.get(),
        name = "Data1",
        email = "Data2@Data3.com",
        password = Cifrador.codifyPassword("Data4"),
        perfil = Perfil.CLIENTE
    )


    @InjectMockKs
    private lateinit var usuariosCacheRepository: UsuariosCacheRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        val res = usuariosCacheRepository.findAll().toList()
        assertEquals(0, res.size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = usuariosCacheRepository.delete(usuario)
        assertFalse(res)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = usuariosCacheRepository.save(usuario)
        assertEquals(usuario.email, res.email)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        val res = usuariosCacheRepository.findById(usuario.id)
        res?.let {
            assertEquals(usuario.id, res.id)
        }
    }
}