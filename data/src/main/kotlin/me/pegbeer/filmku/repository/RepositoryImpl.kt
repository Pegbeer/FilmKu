package me.pegbeer.filmku.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import me.pegbeer.filmku.dto.MovieDto
import me.pegbeer.filmku.local.LocalDataService
import me.pegbeer.filmku.local.entity.GenreEntity
import me.pegbeer.filmku.local.entity.MovieWithGenres
import me.pegbeer.filmku.mapper.GenreMapper
import me.pegbeer.filmku.mapper.MovieDetailMapper
import me.pegbeer.filmku.mapper.MovieMapper
import me.pegbeer.filmku.model.MovieDetail
import me.pegbeer.filmku.pagination.MoviePagingSource
import me.pegbeer.filmku.remote.RemoteDataService
import me.pegbeer.filmku.util.Result
import me.pegbeer.filmku.util.SortBy
import me.pegbeer.filmku.util.resultFlow

class RepositoryImpl(
    private val local: LocalDataService,
    private val network:RemoteDataService
) : Repository {


    override fun getNowPlayingMovies(page:Int): Flow<PagingData<MovieDetail>> = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { MoviePagingSource(local,network,SortBy.NowPlaying,page) }
    ).flow

    override suspend fun getMovieDetail(id: Long): Result<MovieDetail?> {
        val movieDetailDto = network.getMovieDetails(id)
        if(movieDetailDto.status != Result.Status.SUCCESS) return Result.error(movieDetailDto.code!!)
        val movieDetail = MovieDetailMapper.toMovieDetail(movieDetailDto.data!!)
        return Result.success(movieDetail)
    }

    override fun getPopularMovies(): Flow<Result<List<MovieDetail>>> = flow {
        val result = network.getMovies(sortBy = SortBy.TopRated)
        when(result.status){
            Result.Status.SUCCESS ->{
                val movies = result.data!!.results

                val moviesWithGenres = movies.map {
                    val response = network.getMovieDetails(it.id)
                    println(response.toString())
                    if (response.status == Result.Status.SUCCESS) {
                        MovieDetailMapper.toMovieDetail(response.data!!)
                    } else {
                        MovieDetailMapper.toMovieDetail(it)
                    }
                }.subList(0,5)
                emit(Result.success(moviesWithGenres))
            }
            else ->{
                emit(Result.error(result.code!!))
            }
        }
    }

    companion object{
        const val NETWORK_PAGE_SIZE = 5
    }
}