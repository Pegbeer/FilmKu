package me.pegbeer.filmku.dto

import kotlinx.serialization.Serializable
import me.pegbeer.filmku.dto.GenreDto

@Serializable
data class GenreResponseDto(
    val list: List<GenreDto>
)
