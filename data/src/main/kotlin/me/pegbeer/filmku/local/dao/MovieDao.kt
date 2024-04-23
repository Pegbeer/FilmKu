package me.pegbeer.filmku.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import me.pegbeer.filmku.local.entity.GenreEntity
import me.pegbeer.filmku.local.entity.MovieEntity
import me.pegbeer.filmku.local.entity.MovieWithGenres

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie:MovieEntity):Long

    @Query("SELECT * FROM movieentity")
    suspend fun getAll():List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllGenres(genres:List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllMovies(movies:List<MovieEntity>)

    @Query("SELECT * FROM genreentity")
    fun getAllGenres():Flow<List<GenreEntity>>


    @Transaction
    @Query("SELECT * FROM movieentity WHERE id = :id")
    suspend fun getMovieById(id:Long):MovieWithGenres?
}