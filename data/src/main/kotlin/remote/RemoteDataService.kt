package remote

import dto.MovieDto
import dto.ResponseDto
import kotlinx.coroutines.flow.Flow
import util.Result

interface RemoteDataService {

    suspend fun getNowPlayingMovies(page:Int = 1):Result<ResponseDto>
}