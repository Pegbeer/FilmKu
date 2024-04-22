package me.pegbeer.filmku.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val id:Long,
    val name:String
)