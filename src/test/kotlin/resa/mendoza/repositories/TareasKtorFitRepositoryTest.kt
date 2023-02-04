package resa.mendoza.repositories

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import resa.mendoza.utils.Cifrador

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class TareasKtorFitRepositoryTest {
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


    @InjectMockKs
    private lateinit var tareasKtorFitRepository: TareasKtorFitRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun uploadTarea() = runTest {
        val res = tareasKtorFitRepository.uploadTarea(tarea)
        assertEquals(tarea, res)
    }
}