package me.pegbeer.filmku.local

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

}