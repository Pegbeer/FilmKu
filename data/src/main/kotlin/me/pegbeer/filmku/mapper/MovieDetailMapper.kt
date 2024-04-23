package me.pegbeer.filmku.mapper

import me.pegbeer.filmku.dto.MovieDetailDto
import me.pegbeer.filmku.local.entity.MovieWithGenres
import me.pegbeer.filmku.model.MovieDetail

class MovieDetailMapper {
    companion object{
        fun toMovieDetail(movieEntity: MovieWithGenres,movieDetailDto: MovieDetailDto):MovieDetail{
            return MovieDetail(
                movieEntity.movie.id,
                movieDetailDto.length,
                movieEntity.movie.title,
                movieEntity.movie.overview,
                movieEntity.movie.posterPath,
                movieEntity.movie.releaseDate,
                movieEntity.movie.voteAverage,
                movieEntity.movie.language,
                movieEntity.genres,
                movieDetailDto.credits.cast
            )
        }
    }
}