package resa.mendoza.repositories

import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import resa.mendoza.models.maquina.Personalizadora

interface MaquinaPersonalizadoraRepository : CoroutineCrudRepository<Personalizadora, ObjectId> {
}