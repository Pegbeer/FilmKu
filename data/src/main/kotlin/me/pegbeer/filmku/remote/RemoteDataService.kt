package me.pegbeer.filmku.remote

import me.pegbeer.filmku.dto.GenreResponseDto
import me.pegbeer.filmku.dto.MovieDetailDto
import me.pegbeer.filmku.dto.ResponseDto
import me.pegbeer.filmku.util.Result
import me.pegbeer.filmku.util.SortBy


interface RemoteDataService {
    suspend fun getMovies(page:Int = 1,sortBy: SortBy):Result<ResponseDto>
    suspend fun getMovieDetails(id:Long):Result<MovieDetailDto>
}