package local.converters

import androidx.room.TypeConverter

class GenreIdsConverter {

    @TypeConverter
    fun fromString(value: String): List<Int> {
        return value.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun fromList(value: List<Int>): String {
        return value.joinToString(",")
    }
}