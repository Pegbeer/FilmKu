package me.pegbeer.filmku.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.pegbeer.filmku.dto.MovieDto
import me.pegbeer.filmku.local.LocalDataService
import me.pegbeer.filmku.mapper.MovieMapper
import me.pegbeer.filmku.remote.RemoteDataService
import me.pegbeer.filmku.util.Result

class MoviePagingSource(
    private val local:LocalDataService,
    private val network:RemoteDataService,
    private val page:Int
): PagingSource<Int, MovieDto>() {

    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        return try {
            val nextPage = params.key ?: page
            val response = network.getNowPlayingMovies(nextPage)
            if (response.status == Result.Status.SUCCESS) {
                val movies = response.data!!.results.map { MovieMapper.toMovieEntity(it) }
                local.insertAllMovies(movies)
            }
            LoadResult.Page(
                data = local.getAllMovies().map { MovieMapper.toMovieDto(it) },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.data!!.results.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}