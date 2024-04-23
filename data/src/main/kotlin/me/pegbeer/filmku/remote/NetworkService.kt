package me.pegbeer.filmku.remote

import me.pegbeer.filmku.dto.GenreResponseDto
import me.pegbeer.filmku.dto.MovieDetailDto
import me.pegbeer.filmku.dto.ResponseDto
import me.pegbeer.filmku.util.Result

class NetworkService(
    private val apiService: ApiService
) : RemoteDataService, NetworkRequest(){

    override suspend fun getNowPlayingMovies(page: Int): Result<ResponseDto>{
        return apiService.getMovies().getResult()
    }

    override suspend fun downloadGenres(): Result<GenreResponseDto> {
        return apiService.getAllGenres().getResult()
    }

    override suspend fun getMovieDetails(id: Long): Result<MovieDetailDto> {
        return apiService.getMovieDetail(id).getResult()
    }
}