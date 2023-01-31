package resa.mendoza

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MongoDbSpringDataReactivoApplication

fun main(args: Array<String>) {
	runApplication<MongoDbSpringDataReactivoApplication>(*args)
}
