package resa.mendoza.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
sealed class Response<T>

@Serializable
class ResponseSuccess<T : Any>(val code: Int, val data: T) : Response<T>()

@Serializable
class ResponseFailure(val code: Int, val message: String) : Response<@Contextual Nothing>()