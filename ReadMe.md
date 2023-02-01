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

## Diseño

# Introducción

Para la realización de este proyecto, se puso de requisito diseñarla usando MongoDB + Spring Data de manera reactiva.

La **documentación** se encuentra en el directorio **"documentation"** en la raíz del proyecto, además del enunciado del
problema.

El primer paso fue el diseño del *diagrama de clases*, cuyo resultado final fue el siguiente:

# Diagrama de clases

![DiagramaClases](image/tennisLab-Reactivo.jpg)

Las distintas relaciones se encuentran completamente explicadas y detalladas en la *documentación*.

# Configuración del proyecto

La configuración del proyecto se realizó utilizando [Gradle](https://gradle.org/); nos apoyamos en las siguientes
tecnologías:

# Configuración de la base de datos

Luego de proponer las distintas opciones de uso de MongoDB, acabamos
eligiendo [MongoDB Atlas](https://www.mongodb.com/atlas/database), que nos permite conectarnos de manera rápida y
sencilla.

## Estructura del proyecto

Para mantener el proyecto ordenado, se opto por una estructuración de clases referenciando sus
funciones, dando como resultado los distintos paquetes como: *"Models", "Repositories"...*

# Documentación

Las clases se encuentran documentadas con KDoc, y hemos implantado **Dokka** para poder presentar una documentación en
formato *HTML*.

Luego, en la raíz del proyecto, en el directorio *"documentation"*, se encuentra la documentación en formato *PDF* que
detalla el proyecto en gran medida.

# Funcionamiento de la aplicación

## Tests

## Autores

[Sebastián Mendoza](https://github.com/SebsMendoza) y [Mario Resa](https://github.com/Mario999X)