package me.pegbeer.filmku.remote

import me.pegbeer.filmku.dto.ResponseDto
import me.pegbeer.filmku.util.Result

interface RemoteDataService {

    suspend fun getNowPlayingMovies(page:Int = 1):Result<ResponseDto>
}