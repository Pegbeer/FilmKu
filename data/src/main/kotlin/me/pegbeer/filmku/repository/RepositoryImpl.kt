package me.pegbeer.filmku.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import me.pegbeer.filmku.dto.MovieDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import me.pegbeer.filmku.local.LocalDataService
import me.pegbeer.filmku.local.entity.GenreEntity
import me.pegbeer.filmku.mapper.GenreMapper
import me.pegbeer.filmku.pagination.MoviePagingSource
import me.pegbeer.filmku.remote.RemoteDataService
import me.pegbeer.filmku.util.Result

class RepositoryImpl(
    private val local: LocalDataService,
    private val network:RemoteDataService
) : Repository {

    override fun getNowPlayingMovies(page:Int): Flow<PagingData<MovieDto>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(local,network,page) }
        ).flow
    }

    override fun saveGenres(): Flow<Result<List<GenreEntity>>> = flow {
        emit(Result.loading())
        val response = network.downloadGenres()
        if(response.status != Result.Status.SUCCESS){
            emit(Result.error(response.code!!))
        }
        val genresEntities = response.data!!.list.map { GenreMapper.toGenreEntity(it) }
        local.insertAllGenres(genresEntities)

        emit(Result.success(genresEntities))
    }

    companion object{
        const val NETWORK_PAGE_SIZE = 5
    }
}