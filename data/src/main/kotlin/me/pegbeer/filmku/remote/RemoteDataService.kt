package me.pegbeer.filmku.remote

import me.pegbeer.filmku.dto.GenreResponseDto
import me.pegbeer.filmku.dto.MovieDetailDto
import me.pegbeer.filmku.dto.ResponseDto
import me.pegbeer.filmku.util.Result


interface RemoteDataService {
    suspend fun downloadGenres():Result<GenreResponseDto>
    suspend fun getNowPlayingMovies(page:Int = 1):Result<ResponseDto>
    suspend fun getMovieDetails(id:Long):Result<MovieDetailDto>
}