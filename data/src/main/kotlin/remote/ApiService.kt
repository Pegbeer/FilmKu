package remote


import dto.GenreResponseDto
import dto.ResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("3/discover/movie")
    suspend fun getMovies(
        @Query("page")page:Int = 1
    ):Response<ResponseDto>


    @GET("3/genre/movie/list")
    suspend fun getAllGenres():Response<GenreResponseDto>
}