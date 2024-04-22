package local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import local.converters.GenreIdsConverter

@Entity
@TypeConverters(GenreIdsConverter::class)
data class MovieEntity(
    @PrimaryKey
    val id:Long,
    val overview:String,
    val posterPath:String,
    val releaseDate:String,
    val title:String,
    val voteAverage:Double,
    val genresIds:List<Long>
)