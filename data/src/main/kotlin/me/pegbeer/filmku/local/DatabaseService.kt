package me.pegbeer.filmku.local

import kotlinx.coroutines.flow.Flow
import me.pegbeer.filmku.local.entity.GenreEntity
import me.pegbeer.filmku.local.entity.MovieEntity
import me.pegbeer.filmku.local.entity.MovieWithGenres

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

    override fun getAllGenres(): Flow<List<GenreEntity>> {
        return database.movieDao.getAllGenres()
    }

    override suspend fun getMovieWithGenres(id: Long): MovieWithGenres? {
        return database.movieDao.getMovieById(id)
    }
}