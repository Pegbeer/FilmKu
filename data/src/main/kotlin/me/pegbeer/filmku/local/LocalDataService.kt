package me.pegbeer.filmku.local


import me.pegbeer.filmku.local.entity.GenreEntity
import me.pegbeer.filmku.local.entity.MovieEntity

interface LocalDataService {
    suspend fun getAllMovies():List<MovieEntity>
    suspend fun insertAllMovies(movies:List<MovieEntity>)
    suspend fun insertAllGenres(genres:List<GenreEntity>)
    suspend fun insertMovie(movie:MovieEntity) : Long
}