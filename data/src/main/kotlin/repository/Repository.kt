package repository

import androidx.paging.PagingData
import dto.MovieDto
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getNowPlayingMovies(page:Int):Flow<PagingData<MovieDto>>
}