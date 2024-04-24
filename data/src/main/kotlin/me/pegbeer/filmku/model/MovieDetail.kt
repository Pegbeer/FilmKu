package me.pegbeer.filmku.model

import me.pegbeer.filmku.dto.CastDto
import me.pegbeer.filmku.dto.GenreDto
import me.pegbeer.filmku.local.entity.GenreEntity

data class MovieDetail(
    val id:Long,
    val length:Int,
    val title:String,
    val overview:String,
    val posterPath:String,
    val releaseDate:String,
    val voteRating:Double,
    val language:String,
    val videoKey:String,
    val genres:List<GenreDto>,
    val cast:List<CastDto>
)