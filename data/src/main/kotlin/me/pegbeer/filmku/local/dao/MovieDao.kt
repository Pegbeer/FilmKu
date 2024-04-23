package me.pegbeer.filmku.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.pegbeer.filmku.local.entity.GenreEntity
import me.pegbeer.filmku.local.entity.MovieEntity
import me.pegbeer.filmku.local.entity.MovieWithGenres

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie:MovieEntity):Long

    @Query("SELECT * FROM movieentity WHERE DATE(releaseDate) < DATE() ORDER BY DATE(releaseDate) DESC")
    suspend fun getAll():List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllMovies(movies:List<MovieEntity>)

    @Query("SELECT * FROM movieentity WHERE id = :id")
    suspend fun getMovieById(id:Long):MovieEntity?
}