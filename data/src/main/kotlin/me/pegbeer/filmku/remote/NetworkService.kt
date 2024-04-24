package me.pegbeer.filmku.remote

import me.pegbeer.filmku.dto.GenreResponseDto
import me.pegbeer.filmku.dto.MovieDetailDto
import me.pegbeer.filmku.dto.ResponseDto
import me.pegbeer.filmku.util.Result
import me.pegbeer.filmku.util.SortBy

class NetworkService(
    private val apiService: ApiService
) : RemoteDataService, NetworkRequest(){

    override suspend fun getMovies(page: Int,sortBy: SortBy): Result<ResponseDto>{
        return when(sortBy){
            SortBy.NowPlaying -> apiService.getNowPlayingMovies(page).getResult()
            else -> apiService.getMovies(page,sortBy.value).getResult()
        }
    }

    override suspend fun getMovieDetails(id: Long): Result<MovieDetailDto> {
        return apiService.getMovieDetail(id).getResult()
    }
}