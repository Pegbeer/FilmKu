package me.pegbeer.filmku.repository

import androidx.paging.PagingData
import me.pegbeer.filmku.dto.MovieDto
import kotlinx.coroutines.flow.Flow
import me.pegbeer.filmku.local.entity.GenreEntity
import me.pegbeer.filmku.local.entity.MovieWithGenres
import me.pegbeer.filmku.model.MovieDetail
import me.pegbeer.filmku.util.Result

interface Repository {
    fun getNowPlayingMovies(page:Int = 1):Flow<PagingData<MovieDetail>>
    fun getPopularMovies():Flow<Result<List<MovieDetail>>>
    suspend fun getMovieDetail(id:Long):Result<MovieDetail>
}