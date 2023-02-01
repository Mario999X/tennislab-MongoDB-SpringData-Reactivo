package resa.mendoza

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import resa.mendoza.controller.*
import resa.mendoza.db.*
import resa.mendoza.models.*
import resa.mendoza.utils.CalculoPrecioTarea

@SpringBootApplication
class MongoDbSpringDataReactivoApplication
@Autowired constructor(
    private val productoController: ProductoController,
    private val usuarioController: UsuarioController,
    private val encordarController: EncordarController,
    private val personalizarController: PersonalizarController,
    private val adquisicionController: AdquisicionController
) : CommandLineRunner {
    override fun run(vararg args: String?): Unit = runBlocking {
        // Listas
        val productosList = mutableListOf<Producto>()
        val encordacionesList = mutableListOf<Encordar>()
        val personalizacionesList = mutableListOf<Personalizar>()
        val adquisicionesList = mutableListOf<Adquisicion>()

        // Obtencion de datos
        val productosInit = getProductoInit()
        val encordacionesInit = getEncordaciones()
        val personalizacionesInit = getPersonalizaciones()
        val adquisicionInit = getAdquisicionInit()

        val clear = launch {
            productoController.resetProductos()
            usuarioController.resetUsuariosMongo()
            encordarController.resetEncordaciones()
            personalizarController.resetPersonalizaciones()
            adquisicionController.resetAdquisiciones()
        }
        clear.join()

        // Usuarios
        println("Usuarios")
        val usuariosList = usuarioController.getAllUsuariosApi().toList().toMutableList()
        usuariosList[0].raqueta = getRaquetasInit()
        usuariosList[9].perfil = Perfil.ENCORDADOR
        usuariosList[8].perfil = Perfil.ADMIN

        // Create CACHE-MONGO
        usuariosList.forEach {
            usuarioController.createUsuario(it)
            println(it)
        }
        // FindAll CACHE-MONGO
        usuarioController.getAllUsuariosCache().collect {
            println(it)
        }
        usuarioController.getAllUsuariosMongo().collect {
            println(it)
        }
        // FindById -> Cache -> Mongo
        val userId = usuarioController.getUsuarioById(usuariosList[1].id)
        userId?.let { println(it) }
        // Update -> Cache && Mongo
        userId?.let {
            it.name = "Solid Snake"
            usuarioController.createUsuario(it)
        }
        // Delete -> Cache && Mongo
        val userDelete = usuarioController.getUsuarioById(usuariosList[2].id)
        userDelete?.let {
            usuarioController.deleteUsuario(it)
        }

        val init = launch {
            productosInit.forEach { productoController.createProducto(it) }
            productosList.clear()
            productoController.getProductos().collect { productosList.add(it) }
            println("Productos")
            productosList.forEach { println(it) }

            encordacionesInit.forEach { encordarController.createEncordar(it) }
            encordacionesList.clear()
            encordarController.getEncordaciones().collect { encordacionesList.add(it) }
            println("Encordaciones")
            encordacionesList.forEach { println(it) }

            personalizacionesInit.forEach { personalizarController.createPersonalizaciones(it) }
            personalizacionesList.clear()
            personalizarController.getPersonalizaciones().collect { personalizacionesList.add(it) }
            println("Personalizaciones")
            personalizacionesList.forEach { println(it) }

            adquisicionInit.forEach { adquisicionController.createAdquisicion(it) }
            adquisicionesList.clear()
            adquisicionController.getAdquisiciones().collect { adquisicionesList.add(it) }
            println("Adquisiciones")
            adquisicionesList.forEach { println(it) }
        }
        init.join()

        delay(1000)

        val update = launch {
            // Productos
            println("\tProductos")
            // GetById
            val producto = productoController.getProductoById(productosList[1].id)
            producto?.let { println(it) }
            // Update
            producto?.let {
                it.precio += 3.0
                productoController.createProducto(it)
            }
            // Delete
            val productoDelete = productoController.getProductoById(productosList[0].id)
            productoDelete?.let {
                productoController.deleteProducto(it)
            }

            // Encordados
            println("\tEncordados")
            // GetById
            val encordado = encordarController.getEncordarById(encordacionesList[1].id)
            encordado?.let { println(it) }
            // Update
            encordado?.let {
                it.informacionEndordado = "Data69"
                encordarController.createEncordar(it)
            }
            // Delete
            val encordarDelete = encordarController.getEncordarById(encordacionesList[0].id)
            encordarDelete?.let {
                encordarController.deleteEncordacion(it)
            }

            // Personalizaciones
            println("\tPersonalizaciones")
            // GetById
            val personalizacion = personalizarController.getPersonalizacionById(personalizacionesList[1].id)
            personalizacion?.let { println(it) }
            // Update
            personalizacion?.let {
                it.informacionPersonalizacion = "Data80"
                personalizarController.createPersonalizaciones(it)
            }
            // Delete
            val personalizarDelete = personalizarController.getPersonalizacionById(personalizacionesList[0].id)
            personalizarDelete?.let {
                personalizarController.deletePersonalizacion(it)
            }

            // Adquisiciones
            println("\tAdquisiciones")
            // GetById
            val adquisicion = adquisicionController.getAdquisicionById(adquisicionesList[1].id)
            adquisicion?.let { println(it) }
            // Update
            adquisicion?.let {
                it.cantidad += 1
                it.precio = CalculoPrecioTarea.calculatePrecio(it.producto.precio, null, null) * it.cantidad
                adquisicionController.createAdquisicion(it)
            }
            // Delete
            val adquisicionDelete = adquisicionController.getAdquisicionById(adquisicionesList[0].id)
            adquisicionDelete?.let {
                adquisicionController.deleteAdquisicion(it)
            }

        }

        update.join()
    }
}

fun main(args: Array<String>) {
    runApplication<MongoDbSpringDataReactivoApplication>(*args)
}
