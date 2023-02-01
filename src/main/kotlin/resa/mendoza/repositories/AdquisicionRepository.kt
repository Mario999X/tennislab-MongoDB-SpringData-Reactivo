package resa.mendoza.repositories

/**
 * @author Mario Resa y Sebastián Mendoza
 */

import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import resa.mendoza.models.Adquisicion

interface AdquisicionRepository : CoroutineCrudRepository<Adquisicion, ObjectId> {
}