package repository

import dto.MovieDto

interface Repository {
    suspend fun save(movie:MovieDto):Long

    suspend fun getNowPlayingMovies():List<MovieDto>
}