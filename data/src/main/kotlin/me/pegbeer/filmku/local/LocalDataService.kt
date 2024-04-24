package me.pegbeer.filmku.local


import kotlinx.coroutines.flow.Flow
import me.pegbeer.filmku.local.entity.GenreEntity
import me.pegbeer.filmku.local.entity.MovieEntity
import me.pegbeer.filmku.local.entity.MovieWithGenres

interface LocalDataService {
    suspend fun getAllMovies():List<MovieEntity>
    suspend fun insertAllMovies(movies:List<MovieEntity>)
}