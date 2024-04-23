package me.pegbeer.filmku.local.entity

import androidx.room.Embedded
import androidx.room.Relation


data class MovieWithGenres(
    @Embedded val movie:MovieEntity,
    @Relation(
        parentColumn = "genresIds",
        entityColumn = "id"
    )
    val genres:List<GenreEntity>
)