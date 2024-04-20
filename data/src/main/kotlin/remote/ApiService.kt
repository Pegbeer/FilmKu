package remote

import dto.MovieDto
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("3/movie/now_playing?language=en-US")
    suspend fun getNowPlayingMovies():Response<List<MovieDto>>
}