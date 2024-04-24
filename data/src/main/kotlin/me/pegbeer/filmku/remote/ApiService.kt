package me.pegbeer.filmku.remote


import me.pegbeer.filmku.dto.GenreResponseDto
import me.pegbeer.filmku.dto.MovieDetailDto
import me.pegbeer.filmku.dto.ResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("/3/discover/movie?sort_by=popularity.desc")
    suspend fun getNowPlayingMovies(
        @Query("page")page:Int = 1,
    ):Response<ResponseDto>

    @GET("/3/discover/movie")
    suspend fun getMovies(
        @Query("page")page:Int = 1,
        @Query("sort_by")sortBy:String
    ):Response<ResponseDto>

    @GET("/3/movie/{id}?append_to_response=credits,videos")
    suspend fun getMovieDetail(
        @Path("id")id:Long
    ):Response<MovieDetailDto>
}