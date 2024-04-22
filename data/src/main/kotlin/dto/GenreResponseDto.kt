package dto

import kotlinx.serialization.Serializable

@Serializable
data class GenreResponseDto(
    val list: List<GenreDto>
)
