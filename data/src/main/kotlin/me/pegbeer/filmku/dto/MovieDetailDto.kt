package me.pegbeer.filmku.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDto(
    @SerializedName("id")
    val id:Long,
    @SerializedName("runtime")
    val length:Int,
    @SerializedName("credits")
    val credits:CreditsDto
)