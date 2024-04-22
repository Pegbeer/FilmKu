package mapper

import dto.MovieDto
import local.entity.MovieEntity

class MovieMapper {
    companion object{

        fun toMovieDto(movie:MovieEntity):MovieDto{
            return MovieDto(
                movie.id,
                movie.overview,
                movie.posterPath,
                movie.title,
                movie.voteAverage,
                movie.genresIds,
                movie.releaseDate
            )
        }

        fun toMovieEntity(movie:MovieDto):MovieEntity{
            return MovieEntity(
                movie.id,
                movie.overview,
                movie.posterPath,
                movie.releaseDate,
                movie.title,
                movie.voteAverage,
                movie.genresIds
            )
        }
    }
}