package me.pegbeer.filmku.dto

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MovieDetailDeserializer : JsonDeserializer<MovieDetailDto> {

    private val castType = TypeToken.getParameterized(List::class.java,CastDto::class.java).type
    private val genresType = TypeToken.getParameterized(List::class.java,GenreDto::class.java).type
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): MovieDetailDto {
        val jsonObject = json?.asJsonObject
        val id = jsonObject?.get("id")?.asLong ?: 0L
        val overview = jsonObject?.get("overview")?.asString ?: ""
        val posterPath = jsonObject?.get("poster_path")?.asString ?: ""
        val title = jsonObject?.get("title")?.asString ?: ""
        val voteAverage = jsonObject?.get("vote_average")?.asDouble ?: 0.0
        val length = jsonObject?.get("runtime")?.asInt ?: 0
        val language = jsonObject?.get("original_language")?.asString ?: ""
        val originalTitle = jsonObject?.get("original_title")?.asString ?: ""
        val releaseDate = jsonObject?.get("release_date")?.asString ?: ""
        val creditsJson = jsonObject?.get("credits")?.asJsonObject
        val castListJson = creditsJson?.get("cast")?.asJsonArray
        val cast = context?.deserialize<List<CastDto>>(castListJson,castType) ?: emptyList()
        val genresJson = jsonObject?.get("genres")?.asJsonArray
        val genres = context?.deserialize<List<GenreDto>>(genresJson,genresType) ?: emptyList()
        val videosJson = jsonObject?.get("videos")?.asJsonObject
        val videosListJson = videosJson?.get("results")?.asJsonArray
        val videoKey = videosListJson?.firstOrNull {
            it.isJsonObject && it.asJsonObject.get("name").asString.contains("Trailer")
        }?.asJsonObject?.get("key")?.asString ?: ""

        return MovieDetailDto(
            id,
            overview,
            posterPath,
            title,
            voteAverage,
            length,
            language,
            originalTitle,
            releaseDate,
            videoKey,
            cast,
            genres
        )
    }
}