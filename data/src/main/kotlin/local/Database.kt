package local

import androidx.room.Database
import androidx.room.RoomDatabase
import local.dao.MovieDao
import local.entity.GenreEntity
import local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class,GenreEntity::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract val movieDao:MovieDao
}