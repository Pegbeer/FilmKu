package local

import kotlinx.coroutines.flow.Flow
import local.entity.GenreEntity
import local.entity.MovieEntity

interface LocalDataService {
    suspend fun getAllMovies():List<MovieEntity>
    suspend fun insertAllMovies(movies:List<MovieEntity>)
    suspend fun insertAllGenres(genres:List<GenreEntity>)

    suspend fun insertMovie(movie:MovieEntity) : Long
}