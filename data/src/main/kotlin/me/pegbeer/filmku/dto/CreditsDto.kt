package me.pegbeer.filmku.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsDto(
    @SerializedName("cast")
    val cast:List<CastDto>
)
