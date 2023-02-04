package resa.mendoza.repositories

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.bson.types.ObjectId
import org.springframework.stereotype.Repository
import resa.mendoza.models.Usuario
import resa.mendoza.services.cache.UsuariosCache

@Repository
class UsuariosCacheRepository {

    private val cacheUsuarios = UsuariosCache()
    private var refreshJob: Job? = null

    private var listaBusquedas = mutableListOf<Usuario>()

    init {
        refreshCache()
    }

    suspend fun findAll(): Flow<Usuario> {
        println("\tFindALL CACHE")
        return cacheUsuarios.cache.asMap().values.asFlow()
    }

    suspend fun delete(entity: Usuario): Boolean {
        println("\tDelete CACHE")
        var existe = false
        val usuario = cacheUsuarios.cache.asMap()[entity.id]
        if (usuario != null) {
            listaBusquedas.removeIf { it.id == usuario.id }
            cacheUsuarios.cache.invalidate(entity.id)
            existe = true
        }
        return existe
    }

    suspend fun save(entity: Usuario): Usuario {
        println("\tSave CACHE")
        listaBusquedas.add(entity)
        return entity
    }

    suspend fun findById(id: ObjectId): Usuario? {
        println("\tfindByID CACHE")
        var usuario: Usuario? = null

        cacheUsuarios.cache.asMap().forEach {
            if (it.key == id) {
                usuario = it.value
            }
        }
        return usuario
    }

    private final fun refreshCache() {
        if (refreshJob != null) refreshJob?.cancel()

        refreshJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                println("Refrescando cache usuarios")
                if (listaBusquedas.isNotEmpty()) {
                    listaBusquedas.forEach {
                        val user = it
                        cacheUsuarios.cache.put(user.id, user)
                    }

                    listaBusquedas.clear()

                    println("Cache actualizada: ${cacheUsuarios.cache.asMap().size}")
                }
                delay(cacheUsuarios.refreshTime.toLong())
            }
        }
    }
}