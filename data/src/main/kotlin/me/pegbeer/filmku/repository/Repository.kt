package me.pegbeer.filmku.repository

import androidx.paging.PagingData
import me.pegbeer.filmku.dto.MovieDto
import kotlinx.coroutines.flow.Flow
import me.pegbeer.filmku.local.entity.GenreEntity
import me.pegbeer.filmku.util.Result

interface Repository {

    fun saveGenres():Flow<Result<List<GenreEntity>>>
    fun getNowPlayingMovies(page:Int):Flow<PagingData<MovieDto>>
}