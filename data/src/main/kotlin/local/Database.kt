package local

import androidx.room.Database
import androidx.room.RoomDatabase
import local.dao.MovieDao
import local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract val movieDao:MovieDao
}