package resa.mendoza.repositories

/**
 * @author Mario Resa y Sebastián Mendoza
 */

import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import resa.mendoza.models.Usuario

/**
 * Repositorio de Usuarios
 */
interface UsuariosMongoRepository : CoroutineCrudRepository<Usuario, ObjectId> {

}