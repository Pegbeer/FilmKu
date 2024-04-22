package me.pegbeer.filmku.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import me.pegbeer.filmku.dto.MovieDto
import kotlinx.coroutines.flow.Flow
import me.pegbeer.filmku.local.LocalDataService
import me.pegbeer.filmku.pagination.MoviePagingSource
import me.pegbeer.filmku.remote.RemoteDataService

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