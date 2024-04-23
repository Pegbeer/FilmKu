package me.pegbeer.filmku.mapper

import me.pegbeer.filmku.dto.MovieDetailDto
import me.pegbeer.filmku.dto.MovieDto
import me.pegbeer.filmku.local.entity.MovieWithGenres
import me.pegbeer.filmku.model.MovieDetail

class MovieDetailMapper {
    companion object{
        fun toMovieDetail(movieDetailDto: MovieDetailDto):MovieDetail{
            return MovieDetail(
                movieDetailDto.id,
                movieDetailDto.length,
                movieDetailDto.title ?: movieDetailDto.originalTitle,
                movieDetailDto.overview,
                movieDetailDto.posterPath,
                movieDetailDto.releaseDate,
                movieDetailDto.voteAverage,
                movieDetailDto.language,
                movieDetailDto.genres,
                movieDetailDto.credits.cast
            )
        }

        fun toMovieDetail(movieDetailDto: MovieDto):MovieDetail{
            return MovieDetail(
                movieDetailDto.id,
                0,
                movieDetailDto.title,
                movieDetailDto.overview,
                movieDetailDto.posterPath,
                movieDetailDto.releaseDate,
                movieDetailDto.voteAverage,
                movieDetailDto.language,
                emptyList(),
                emptyList()
            )
        }
    }
}