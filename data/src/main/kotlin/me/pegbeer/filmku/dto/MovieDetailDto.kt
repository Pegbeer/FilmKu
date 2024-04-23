package me.pegbeer.filmku.dto

import android.icu.text.CaseMap.Title
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDto(
    @SerializedName("id")
    val id:Long,
    @SerializedName("overview")
    val overview:String,
    @SerializedName("poster_path")
    val posterPath:String,
    @SerializedName("title")
    val title:String?,
    @SerializedName("vote_average")
    val voteAverage:Double,
    @SerializedName("runtime")
    val length:Int,
    @SerializedName("original_language")
    val language:String,
    @SerializedName("original_title")
    val originalTitle:String,
    @SerializedName("credits")
    val credits:CreditsDto,
    @SerializedName("release_date")
    val releaseDate:String,
    @SerializedName("genres")
    val genres:List<GenreDto>
)