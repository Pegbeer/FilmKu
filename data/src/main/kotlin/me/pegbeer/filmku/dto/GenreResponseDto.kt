package me.pegbeer.filmku.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import me.pegbeer.filmku.dto.GenreDto

@Serializable
data class GenreResponseDto(
    @SerializedName("genres")
    val genres: List<GenreDto>
)
