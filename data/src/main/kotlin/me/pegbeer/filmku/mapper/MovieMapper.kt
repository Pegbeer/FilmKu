package me.pegbeer.filmku.mapper

import me.pegbeer.filmku.dto.MovieDto
import me.pegbeer.filmku.local.entity.MovieEntity

class MovieMapper {
    companion object{

        fun toMovieDto(movie:MovieEntity): MovieDto {
            return MovieDto(
                movie.id,
                movie.overview,
                movie.posterPath,
                movie.title,
                movie.voteAverage,
                movie.genresIds,
                movie.releaseDate,
                movie.language
            )
        }

        fun toMovieEntity(movie: MovieDto):MovieEntity{
            return MovieEntity(
                movie.id,
                movie.overview,
                movie.posterPath,
                movie.releaseDate,
                movie.title,
                movie.voteAverage,
                movie.genresIds,
                movie.language
            )
        }
    }
}