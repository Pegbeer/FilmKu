package me.pegbeer.filmku.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import me.pegbeer.filmku.dto.MovieDto
import me.pegbeer.filmku.local.LocalDataService
import me.pegbeer.filmku.local.entity.GenreEntity
import me.pegbeer.filmku.mapper.GenreMapper
import me.pegbeer.filmku.mapper.MovieDetailMapper
import me.pegbeer.filmku.model.MovieDetail
import me.pegbeer.filmku.pagination.MoviePagingSource
import me.pegbeer.filmku.remote.RemoteDataService
import me.pegbeer.filmku.util.Result

class RepositoryImpl(
    private val local: LocalDataService,
    private val network:RemoteDataService
) : Repository {


    override fun getNowPlayingMovies(page:Int): Flow<PagingData<MovieDto>> = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { MoviePagingSource(local,network,page) }
    ).flow

    override suspend fun saveGenres(): Result<List<GenreEntity>> {
        val response = network.downloadGenres()
        if(response.status != Result.Status.SUCCESS){
            return Result.error(response.code!!)
        }
        val genresEntities = response.data!!.list.map { GenreMapper.toGenreEntity(it) }
        local.insertAllGenres(genresEntities)

        return Result.success(genresEntities)
    }

    override suspend fun getMovieDetail(id: Long): Result<MovieDetail> {
        val movieEntity = local.getMovieWithGenres(id) ?: return Result.error(500)
        val movieDetailDto = network.getMovieDetails(id)
        if(movieDetailDto.status != Result.Status.SUCCESS) return Result.error(movieDetailDto.code!!)
        val movieDetail = MovieDetailMapper.toMovieDetail(movieEntity,movieDetailDto.data!!)
        return Result.success(movieDetail)
    }

    companion object{
        const val NETWORK_PAGE_SIZE = 5
    }
}