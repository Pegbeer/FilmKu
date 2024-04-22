package me.pegbeer.filmku.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.pegbeer.filmku.local.dao.MovieDao
import me.pegbeer.filmku.local.entity.GenreEntity
import me.pegbeer.filmku.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class,GenreEntity::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract val movieDao: MovieDao
}