package resa.mendoza.db

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import resa.mendoza.models.*

fun getProductoInit() = listOf(
    Producto(
        tipo = Tipo.RAQUETA,
        descripcion = "Babolat Pure Air",
        stock = 3,
        precio = 279.95
    ),
    Producto(
        tipo = Tipo.COMPLEMENTO,
        descripcion = "Wilson Dazzle",
        stock = 5,
        precio = 7.90
    )
)

//fun getAdquisicionInit() = listOf(
//    Adquisicion(
//        cantidad = 1,
//        producto = getProductoInit()[0],
//    ),
//    Adquisicion(
//        cantidad = 2,
//        producto = getProductoInit()[1],
//    ),
//)
fun getEncordaciones() = listOf(
    Encordar(
        informacionEndordado = "Dato1"
    ),
    Encordar(
        informacionEndordado = "Dato2"
    )
)

fun getPersonalizaciones() = listOf(
    Personalizar(
        informacionPersonalizacion = "Dato1"
    ),
    Personalizar(
        informacionPersonalizacion = "Dato2"
    )
)

fun getRaquetasInit() = listOf(
    Raqueta(
        descripcion = "Wilson Burn"
    ),
    Raqueta(
        descripcion = "Babolat Pure Aero"
    )
)

//fun getEncordadorasInit() = listOf(
//    Encordadora(
//        descripcion = "Toshiba ABC",
//        fechaAdquisicion = LocalDate.now().toString(),
//        numSerie = 120L,
//        isManual = true,
//        tensionMax = 23.2,
//        tensionMin = 20.5
//    ),
//    Encordadora(
//        descripcion = "Vevor",
//        fechaAdquisicion = LocalDate.now().toString(),
//        numSerie = 320L,
//        isManual = true,
//        tensionMax = 20.2,
//        tensionMin = 17.5
//    )
//)
//
//fun getPersonalizadorasInit() = listOf(
//    Personalizadora(
//        descripcion = "Toshiba ABC",
//        fechaAdquisicion = LocalDate.now().toString(),
//        numSerie = 540L,
//        maniobrabilidad = true,
//        balance = false,
//        rigidez = false
//    ),
//    Personalizadora(
//        descripcion = "Vevor",
//        fechaAdquisicion = LocalDate.now().toString(),
//        numSerie = 940L,
//        maniobrabilidad = true,
//        balance = true,
//        rigidez = false
//    )
//)
