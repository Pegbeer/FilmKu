package me.pegbeer.filmku.util

import me.pegbeer.filmku.dto.CastDto
import me.pegbeer.filmku.dto.CreditsDto
import me.pegbeer.filmku.dto.GenreDto
import me.pegbeer.filmku.dto.GenreResponseDto
import me.pegbeer.filmku.dto.MovieDetailDto
import me.pegbeer.filmku.dto.MovieDto
import me.pegbeer.filmku.dto.ResponseDto
import me.pegbeer.filmku.local.entity.GenreEntity
import me.pegbeer.filmku.local.entity.MovieEntity

object DataUtil {

    val movieDto = MovieDto(
        1L,
        "",
        "",
        "",
        1.0,
        listOf(1L),
        "",
        ""
    )

    val listMovieDto = listOf(movieDto)

    val movieEntity = MovieEntity(
        1L,
        "",
        "",
        "",
        "",
        1.0,
        listOf(1L),
        ""
    )

    val listMovieEntity = listOf(movieEntity)


    val responseDto = ResponseDto(1, listMovieDto,1,1)

    val genreDto = GenreDto(1L,"")

    val genreEntity = GenreEntity(1L,"")

    val genresEntities = listOf(genreEntity)

    val genreResponseDto = GenreResponseDto(listOf(genreDto))

    val castDto = CastDto(1L,"","")

    val creditsDto = CreditsDto(listOf(castDto))

    val movieDetailDto = MovieDetailDto(
        1L, "","",
        "",1.0,60,
        "", "","",
        "s_76M4c4LTo", listOf(castDto), listOf(genreDto)
    )
}