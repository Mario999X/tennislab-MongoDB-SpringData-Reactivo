package resa.mendoza

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import resa.mendoza.controller.ProductoController
import resa.mendoza.db.getProductoInit
import resa.mendoza.models.Producto

@SpringBootApplication
class MongoDbSpringDataReactivoApplication
@Autowired constructor(
    private val productoController: ProductoController
) : CommandLineRunner {
    override fun run(vararg args: String?): Unit = runBlocking {
        val productosList = mutableListOf<Producto>()

        val productosInit = getProductoInit()

        val clear = launch {
            productoController.productosDeleteAll()
        }
        clear.join()

        val init = launch {
            productosInit.forEach { productoController.createProducto(it) }
            productosList.clear()
            productoController.getProductos().collect { productosList.add(it) }

            println("Productos")
            productosList.forEach { println(it) }
        }
        init.join()
    }
}

fun main(args: Array<String>) {
    runApplication<MongoDbSpringDataReactivoApplication>(*args)
}
