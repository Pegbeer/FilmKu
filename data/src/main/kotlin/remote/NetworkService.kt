package remote

import dto.MovieDto

class NetworkService(
    private val apiService: ApiService
) {

    suspend fun getNowPlayingMovies():Result<List<MovieDto>>{
        val response = apiService.getNowPlayingMovies()
        if(!response.isSuccessful){
            return Result.failure(RuntimeException("An error has occurred processing the request"))
        }
        return Result.success(response.body() ?: emptyList())
    }

}