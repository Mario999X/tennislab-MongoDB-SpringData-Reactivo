# TennisLab

![image](image/portada.png)
Proyecto de gestión de base de datos de una tienda de raquetas para la asignatura Acceso a Datos del IES Luis Vives
(Leganés).

# Versión MongoDB + Spring Data

## [Versión MongoDB](https://github.com/SebsMendoza/tennislab-MongoDB-Reactivo)

## [Vídeo de presentación]

## Índice

- [Diseño](#diseño)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Funcionamiento de la aplicación](#funcionamiento-de-la-aplicación)
- [Tests](#tests)
- [Autores](#autores)

# Diseño

## Introducción

Para la realización de este proyecto, se puso de requisito diseñarla usando MongoDB + Spring Data de manera reactiva.

La **documentación** se encuentra en el directorio **"documentation"** en la raíz del proyecto, además del enunciado del
problema.

El primer paso fue el diseño del *diagrama de clases*, cuyo resultado final fue el siguiente:

## Diagrama de clases

![DiagramaClases](image/tennisLab-Reactivo.jpg)

Las distintas relaciones se encuentran completamente explicadas y detalladas en la *documentación*.

## Configuración del proyecto

La configuración del proyecto se realizó utilizando [Gradle](https://gradle.org/); nos apoyamos en las siguientes
tecnologías:

- [Spring Data](https://spring.io/projects/spring-data)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Mockk](https://mockk.io/)
- [Corrutinas Kotlin](https://kotlinlang.org/docs/coroutines-overview.html)
- [Logger](https://github.com/oshai/kotlin-logging)
- [Serialization Kotlin JSON](https://github.com/Kotlin/kotlinx.serialization)
- [BCrypt](https://github.com/ToxicBakery/bcrypt-mpp)
- [KSP](https://github.com/google/ksp)
- [KtorFit](https://plugins.gradle.org/plugin/de.jensklingenberg.ktorfit)
- [Cache4k](https://github.com/ReactiveCircus/cache4k)
- [Jackson](https://github.com/FasterXML/jackson-module-kotlin)
- [Dokka](https://github.com/Kotlin/dokka)
- [KDoc](https://kotlinlang.org/docs/kotlin-doc.html)
- [JUnit5](https://junit.org/junit5/)

## Configuración de la base de datos

Luego de proponer las distintas opciones de uso de MongoDB, acabamos
eligiendo [MongoDB Atlas](https://www.mongodb.com/atlas/database), que nos permite conectarnos de manera rápida y
sencilla.

La base de datos trabaja de forma reactiva, gracias a los plugin correspondientes y en el uso de corrutinas y flujos
a lo largo de la aplicación.

# Estructura del proyecto

Para mantener el proyecto ordenado, se opto por una estructuración de clases referenciando sus
funciones, dando como resultado los distintos paquetes como: *"Models", "Repositories"...*

## Documentación

Las clases se encuentran documentadas con KDoc, y hemos implantado **Dokka** para poder presentar una documentación en
formato *HTML*.

Luego, en la raíz del proyecto, en el directorio *"documentation"*, se encuentra la documentación en formato *PDF* que
detalla el proyecto en gran medida.

# Funcionamiento de la aplicación

Contamos con una única clase principal de ejecución, *MongoDbSpringDataReactivoApplication*

## MongoDbSpringDataReactivoApplication

Contamos con una funcion main que será la que se ejecute, mientras, la clase, además de tener inyectados los
controladores en el constructor, tiene implementado **CommandLineRunner**. 

Dentro, en el método *run*, se ejecuta la lógica principal del programa. Generamos una serie de listas que 
se encargaran de guardar la futura información que, generalmente tendremos preparada en un archivo *Data*, 
en el fichero *db*. Cargamos los datos, y eliminamos los datos de las distintas colecciones para tener únicamente 
los datos de la ejecución actual.

Luego, preparamos los *escuchadores* de la colección de **productos**, y realizaremos las distintas operaciones CRUD.
Una vez estas operaciones se hayan ejecutado, se cancelarán los escuchadores de productos, pero la aplicación seguirá en
funcionamiento, debido al refresco de la cache de usuarios.

Spring se encarga de realizar la conexión automáticamente desde el fichero de propiedades, usando la siguiente
línea: *spring.data.mongodb.uri=*

## Ejecución

Hemos configurado el proyecto para que, una vez construido, tengamos un jar que permita su ejecución.

Además, hemos usado **Jackson** para visualizar los elementos de la terminal en formato *JSON*.

Para ver los resultados de ejecución, ademas de la implementación de mensajes por terminal, hemos usado la propia
página de *MongoDB Atlas*, o el plugin *MongoDB* de **Visual Studio Code** para visualizar
los cambios sobre la base de datos.

# Tests

# Autores

[Sebastián Mendoza](https://github.com/SebsMendoza) y [Mario Resa](https://github.com/Mario999X)