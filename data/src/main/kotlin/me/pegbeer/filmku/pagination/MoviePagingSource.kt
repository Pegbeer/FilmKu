package me.pegbeer.filmku.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.pegbeer.filmku.dto.MovieDto
import me.pegbeer.filmku.local.LocalDataService
import me.pegbeer.filmku.local.entity.MovieWithGenres
import me.pegbeer.filmku.mapper.MovieDetailMapper
import me.pegbeer.filmku.mapper.MovieMapper
import me.pegbeer.filmku.model.MovieDetail
import me.pegbeer.filmku.remote.RemoteDataService
import me.pegbeer.filmku.util.Result
import me.pegbeer.filmku.util.SortBy

class MoviePagingSource(
    private val local:LocalDataService,
    private val network:RemoteDataService,
    private val sortBy: SortBy,
    private val page:Int
): PagingSource<Int, MovieDetail>() {

    override fun getRefreshKey(state: PagingState<Int, MovieDetail>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDetail> {
        return try {
            val nextPage = params.key ?: page
            val response = network.getMovies(nextPage,sortBy)
            val movies = response.data?.results ?: emptyList()
            if (response.status == Result.Status.SUCCESS) {
                local.insertAllMovies(movies.map { MovieMapper.toMovieEntity(it) })
            }
            LoadResult.Page(
                data = local.getAllMovies().map {
                    val movieDetails = network.getMovieDetails(it.id)
                    if(movieDetails.status == Result.Status.SUCCESS){
                        MovieDetailMapper.toMovieDetail(movieDetails.data!!)
                    }else {
                        MovieDetail(
                            it.id,0,it.title,it.overview,
                            it.posterPath,it.releaseDate,it.voteAverage,it.language,
                            emptyList(), emptyList()
                        )
                    }
                },
                prevKey = if (nextPage == 1) null else (nextPage - 1),
                nextKey = if (nextPage == response.data!!.totalPages) null else (nextPage + 1)
            )
        } catch (e: Exception) {
            Log.e("MoviePagingSource","load: ${e.message}")
            LoadResult.Error(e)
        }
    }
}