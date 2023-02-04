package resa.mendoza

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onStart
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
import resa.mendoza.models.maquina.Encordadora
import resa.mendoza.models.maquina.Personalizadora
import resa.mendoza.utils.CalculoPrecioTarea
import java.time.LocalDateTime

@SpringBootApplication
class MongoDbSpringDataReactivoApplication
@Autowired constructor(
    private val productoController: ProductoController,
    private val usuarioController: UsuarioController,
    private val encordarController: EncordarController,
    private val personalizarController: PersonalizarController,
    private val adquisicionController: AdquisicionController,
    private val tareaController: TareaController,
    private val pedidoController: PedidoController,
    private val encordadoraController: MaquinaEncordadoraController,
    private val personalizadoraController: MaquinaPersonalizadoraController
) : CommandLineRunner {
    override fun run(vararg args: String?): Unit = runBlocking {
        // Listas
        val productosList = mutableListOf<Producto>()
        val encordacionesList = mutableListOf<Encordar>()
        val personalizacionesList = mutableListOf<Personalizar>()
        val adquisicionesList = mutableListOf<Adquisicion>()
        val maquinaEncordadoraList = mutableListOf<Encordadora>()
        val maquinaPersonalizadoraList = mutableListOf<Personalizadora>()

        // Obtención de datos
        val productosInit = getProductoInit()
        val encordacionesInit = getEncordaciones()
        val personalizacionesInit = getPersonalizaciones()
        val adquisicionInit = getAdquisicionInit()
        val encordadoraInit = getEncordadorasInit()
        val personalizadoraInit = getPersonalizadorasInit()

        val clear = launch {
            productoController.resetProductos()
            usuarioController.resetUsuariosMongo()
            encordarController.resetEncordaciones()
            personalizarController.resetPersonalizaciones()
            adquisicionController.resetAdquisiciones()
            tareaController.resetTarea()
            pedidoController.resetPedidos()
            encordadoraController.resetEncordadoras()
            personalizadoraController.resetPersonalizadoras()
        }
        clear.join()

        val productosListener = launch {
            productoController.watchProductos()
                .onStart { println("Escuchando cambios en Producto...") }
                .collect { println("Evento: ${it.operationType?.value} -> Producto: ${it.body}") }
        }

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

            encordadoraInit.forEach { encordadoraController.createEncordadora(it) }
            maquinaEncordadoraList.clear()
            encordadoraController.getEncordadoras().collect { maquinaEncordadoraList.add(it) }
            println("Máquinas encordadoras")
            maquinaEncordadoraList.forEach { println(it) }

            personalizadoraInit.forEach { personalizadoraController.createPersonalizadora(it) }
            maquinaPersonalizadoraList.clear()
            personalizadoraController.getPersonalizadoras().collect { maquinaPersonalizadoraList.add(it) }
            println("Máquinas personalizadoras")
            maquinaPersonalizadoraList.forEach { println(it) }
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

            //Tareas
            println("\tTareas")
            val tarea1 = Tarea(
                adquisicion = getAdquisicionInit()[1],
                personalizar = getPersonalizaciones()[1],
                usuario = usuarioController.getUsuarioById(usuariosList[9].id)!!,
                raqueta = usuarioController.getUsuarioById(usuariosList[0].id)!!.raqueta?.get(1)
            )
            val tarea2 = Tarea(
                encordar = getEncordaciones()[1],
                usuario = usuarioController.getUsuarioById(usuariosList[9].id)!!,
                raqueta = usuarioController.getUsuarioById(usuariosList[0].id)!!.raqueta?.get(0)
            )

            val tarea3 = Tarea(
                adquisicion = getAdquisicionInit()[1],
                usuario = usuarioController.getUsuarioById(usuariosList[9].id)!!
            )
            //Create
            tareaController.createTarea(tarea1)
            tareaController.createTarea(tarea2)
            tareaController.createTarea(tarea3)
            //FindAll
            tareaController.getTareas().collect { println(it) }
            //FindById
            val tarea = tareaController.getTareaById(tarea1.id)
            tarea?.let { println(it) }
            //Update
            tarea?.let {
                it.precio += 0.20
                tareaController.createTarea(it)
            }
            val tareaDelete = tareaController.getTareaById(tarea2.id)
            tareaDelete?.let {
                tareaController.deleteTarea(it)
            }

            //Pedidos
            println("\tPedidos")
            val pedido1 = Pedido(
                estadoPedido = EstadoPedido.PROCESANDO,
                fechaEntrada = LocalDateTime.now().toString(),
                fechaProgramada = LocalDateTime.now().plusDays(10).toString(),
                cliente = usuarioController.getUsuarioById(usuariosList[0].id)!!,
                tareas = listOf(tarea1, tarea2)
            )
            val pedido2 = Pedido(
                estadoPedido = EstadoPedido.PROCESANDO,
                fechaEntrada = LocalDateTime.now().toString(),
                fechaProgramada = LocalDateTime.now().plusDays(10).toString(),
                cliente = usuarioController.getUsuarioById(usuariosList[0].id)!!,
                tareas = listOf(tarea3)
            )
            //Create
            pedidoController.createPedido(pedido1)
            pedidoController.createPedido(pedido2)
            //FindAll
            pedidoController.getPedidos().collect { println(it) }
            //FindById
            val pedidoId = pedidoController.getPedidoById(pedido1.id)
            pedidoId?.let { println(it) }
            //Update
            pedidoId?.let {
                it.estadoPedido = EstadoPedido.TERMINADO
                pedidoController.createPedido(it)
            }
            //Delete
            pedidoController.deletePedido(pedido2)

            //Encordadoras
            println("\tEncordadoras")
            //FindById
            val encordadoraId = encordadoraController.getEncordadoraById(maquinaEncordadoraList[0].id)
            encordadoraId?.let {
                println(it)
            }
            //Update
            encordadoraId?.let {
                it.isManual = false
                encordadoraController.createEncordadora(it)
            }
            //Delete
            val encordadoraDelete = encordadoraController.getEncordadoraById(maquinaEncordadoraList[1].id)
            encordadoraDelete?.let {
                encordadoraController.deleteEncordadora(it)
            }

            //Personalizadoras
            println("\tPersonalizadoras")
            //FindById
            val personalizadoraId = personalizadoraController.getPersonalizadoraById(maquinaPersonalizadoraList[0].id)
            personalizadoraId?.let {
                println(it)
            }
            //Update
            personalizadoraId?.let {
                it.maniobrabilidad = false
                personalizadoraController.createPersonalizadora(it)
            }
            //Delete
            val personalizadoraDelete =
                personalizadoraController.getPersonalizadoraById(maquinaPersonalizadoraList[1].id)
            personalizadoraDelete?.let {
                personalizadoraController.deletePersonalizadora(it)
            }
        }

        update.join()

        productosListener.cancel()
    }
}

fun main(args: Array<String>) {
    runApplication<MongoDbSpringDataReactivoApplication>(*args)
}
