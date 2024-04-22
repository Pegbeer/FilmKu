package local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import local.entity.GenreEntity
import local.entity.MovieEntity

class DatabaseService(
    private val database: Database
) : LocalDataService {
    override suspend fun getAllMovies(): List<MovieEntity> = database.movieDao.getAll()

    override suspend fun insertAllMovies(movies: List<MovieEntity>) {
        database.movieDao.saveAllMovies(movies)
    }

    override suspend fun insertAllGenres(genres: List<GenreEntity>) {
        database.movieDao.saveAllGenres(genres)
    }

    override suspend fun insertMovie(movie: MovieEntity):Long {
        return database.movieDao.saveMovie(movie)
    }
}