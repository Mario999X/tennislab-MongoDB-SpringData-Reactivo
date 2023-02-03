package resa.mendoza.controller

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import resa.mendoza.models.Adquisicion
import resa.mendoza.models.Producto
import resa.mendoza.models.Tipo

@ExtendWith(MockKExtension::class)
@SpringBootTest
internal class AdquisicionControllerTest {

    @MockK
    private lateinit var controller: AdquisicionController

    private val adquisicionTest = Adquisicion(
        cantidad = 2,
        producto = Producto(
            tipo = Tipo.RAQUETA,
            descripcion = "Babolat Pure Air",
            stock = 3,
            precio = 279.95
        ),
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAdquisiciones() = runTest {

    }

    @Test
    fun createAdquisicion() {
    }

    @Test
    fun getAdquisicionById() {
    }

    @Test
    fun deleteAdquisicion() {
    }

    @Test
    fun resetAdquisiciones() {
    }
}