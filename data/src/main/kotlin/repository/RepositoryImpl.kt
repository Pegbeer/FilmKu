package repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dto.MovieDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import local.LocalDataService
import pagination.MoviePagingSource
import remote.RemoteDataService

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

    companion object{
        const val NETWORK_PAGE_SIZE = 5
    }
}