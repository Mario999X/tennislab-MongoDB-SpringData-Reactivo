package resa.mendoza.repositories

import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import resa.mendoza.models.Personalizar

interface PersonalizarRepository : CoroutineCrudRepository<Personalizar, ObjectId> {
}