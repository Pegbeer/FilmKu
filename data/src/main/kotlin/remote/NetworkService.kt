package remote

import dto.MovieDto
import dto.ResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import util.Result

class NetworkService(
    private val apiService: ApiService
) : RemoteDataService{
    override suspend fun getNowPlayingMovies(page: Int): Result<ResponseDto>{
        val response = apiService.getMovies()
        if(!response.isSuccessful) return Result.error(response.code())

        if(response.body() == null) return Result.error(204)

        return Result.success(response.body()!!)
    }
}