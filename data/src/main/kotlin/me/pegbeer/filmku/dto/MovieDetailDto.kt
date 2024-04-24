package me.pegbeer.filmku.dto

import android.icu.text.CaseMap.Title
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


data class MovieDetailDto(
    val id:Long,
    val overview:String,
    val posterPath:String,
    val title:String?,
    val voteAverage:Double,
    val length:Int,
    val language:String,
    val originalTitle:String,
    val releaseDate:String,
    val videoKey:String,
    val cast:List<CastDto>,
    val genres:List<GenreDto>
)