package me.pegbeer.filmku.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import me.pegbeer.filmku.dto.GenreDto
import me.pegbeer.filmku.dto.MovieDetailDto
import me.pegbeer.filmku.dto.MovieDto
import me.pegbeer.filmku.model.MovieDetail


data class MovieWithGenres(
    val movie: MovieDetailDto,
    val genres: List<GenreDto>
)
