package me.pegbeer.filmku.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    @SerializedName("id")
    val id:Long,
    @SerializedName("overview")
    val overview:String,
    @SerializedName("poster_path")
    val posterPath:String,
    @SerializedName("title")
    val title:String,
    @SerializedName("vote_average")
    val voteAverage:Double,
    @SerializedName("genre_ids")
    val genresIds:List<Long>,
    @SerializedName("release_date")
    val releaseDate:String
)