package me.pegbeer.filmku.mapper

import me.pegbeer.filmku.dto.GenreDto
import me.pegbeer.filmku.local.entity.GenreEntity

class GenreMapper {
    companion object{

        fun toGenreEntity(genreDto: GenreDto):GenreEntity{
            return GenreEntity(genreDto.id,genreDto.name)
        }

        fun toGenreDto(genreEntity: GenreEntity):GenreDto{
            return GenreDto(genreEntity.id,genreEntity.name)
        }
    }
}