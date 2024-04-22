package me.pegbeer.filmku.repository

import androidx.paging.PagingData
import me.pegbeer.filmku.dto.MovieDto
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getNowPlayingMovies(page:Int):Flow<PagingData<MovieDto>>
}