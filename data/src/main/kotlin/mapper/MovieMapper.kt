package mapper

import dto.MovieDto
import local.entity.MovieEntity

class MovieMapper {
    companion object{

        fun toMovieDto(movie:MovieEntity):MovieDto{
            return MovieDto(
                movie.genreIds,
                movie.id,
                movie.overview,
                movie.posterPath,
                movie.title,
                movie.voteAverage,
                movie.originalLanguage
            )
        }

        fun toMovieEntity(movie:MovieDto):MovieEntity{
            return MovieEntity(
                movie.id,
                movie.genresIds,
                movie.overview,
                movie.posterPath,
                movie.title,
                movie.voteAverage,
                movie.originalLanguage
            )
        }
    }
}