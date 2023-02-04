package resa.mendoza.repositories

import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import resa.mendoza.models.Encordar

interface EncordarRepository : CoroutineCrudRepository<Encordar, ObjectId> {
}