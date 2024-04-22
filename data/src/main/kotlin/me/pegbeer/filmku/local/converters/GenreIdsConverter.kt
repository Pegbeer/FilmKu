package me.pegbeer.filmku.local.converters

import androidx.room.TypeConverter

class GenreIdsConverter {

    @TypeConverter
    fun fromString(value: String): List<Long> {
        return value.split(",").map { it.toLong() }
    }

    @TypeConverter
    fun fromList(value: List<Long>): String {
        return value.joinToString(",")
    }
}