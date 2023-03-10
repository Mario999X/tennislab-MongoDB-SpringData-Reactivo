package resa.mendoza.services

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ChangeStreamEvent
import org.springframework.data.mongodb.core.ChangeStreamOptions
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Service
import resa.mendoza.models.Producto

private val logger = KotlinLogging.logger { }

@Service
class ProductoService
@Autowired constructor(
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) {
    fun watch(): Flow<ChangeStreamEvent<Producto>> {
        logger.info { "watch()" }
        return reactiveMongoTemplate.changeStream(
            "productos",
            ChangeStreamOptions.empty(),
            Producto::class.java
        ).asFlow()
    }
}