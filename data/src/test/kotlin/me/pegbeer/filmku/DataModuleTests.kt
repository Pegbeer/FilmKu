package me.pegbeer.filmku

import com.google.gson.GsonBuilder
import kotlinx.coroutines.test.runTest
import me.pegbeer.filmku.dto.GenreDto
import me.pegbeer.filmku.dto.GenreResponseDto
import me.pegbeer.filmku.dto.MovieDto
import me.pegbeer.filmku.dto.ResponseDto
import me.pegbeer.filmku.mapper.MovieMapper
import me.pegbeer.filmku.remote.ApiService
import me.pegbeer.filmku.remote.NetworkService
import me.pegbeer.filmku.remote.RemoteDataService
import me.pegbeer.filmku.util.DataUtil
import me.pegbeer.filmku.util.Result
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.common.truth.Truth.*
import com.google.gson.Gson
import me.pegbeer.filmku.dto.MovieDetailDeserializer
import me.pegbeer.filmku.dto.MovieDetailDto
import me.pegbeer.filmku.remote.NetworkRequest
import me.pegbeer.filmku.util.SortBy
import org.koin.test.KoinTest
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder


class DataModuleTests {

    private lateinit var server: MockWebServer
    private lateinit var apiService:ApiService
    private lateinit var remoteDataService: RemoteDataService


    private val gson = GsonBuilder()
                        .registerTypeAdapter(MovieDetailDto::class.java, MovieDetailDeserializer())
                        .create()

    @Before
    fun setUp(){
        server = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
        remoteDataService = NetworkService(apiService)
    }


    @Test
    fun `Should serialize and deserialize movie dto class`(){
        val jsonString = gson.toJson(DataUtil.movieDto)
        val deserializedJson = gson.fromJson(jsonString, MovieDto::class.java)

        assertEquals(DataUtil.movieDto,deserializedJson)
    }

    @Test
    fun `Should serialize and deserialize genre dto class`(){
        val jsonString = gson.toJson(DataUtil.genreDto)
        val deserializedJson = gson.fromJson(jsonString, GenreDto::class.java)

        assertEquals(DataUtil.genreDto,deserializedJson)
    }

    @Test
    fun `Should serialize and deserialize genre response dto class`(){
        val jsonString = gson.toJson(DataUtil.genreResponseDto)
        val deserializedJson = gson.fromJson(jsonString, GenreResponseDto::class.java)

        assertEquals(DataUtil.genreResponseDto,deserializedJson)
    }

    @Test
    fun `Should serialize and deserialize response dto class`(){
        val jsonString = gson.toJson(DataUtil.responseDto)
        val deserializedJson = gson.fromJson(jsonString, ResponseDto::class.java)

        assertEquals(DataUtil.responseDto,deserializedJson)
    }

    @Test
    fun `Should deserialize movie detail dto class`(){
        val input = javaClass.classLoader?.getResourceAsStream("movie_detail_dto_dummy_data.json")
        val reader = InputStreamReader(input, Charsets.UTF_8)
        val deserializedJson = gson.fromJson(reader,MovieDetailDto::class.java)
        assertEquals(deserializedJson,DataUtil.movieDetailDto)
    }

    @Test
    fun `Should return an 400 code on error`() = runTest{
        val emptyDto = emptyList<MovieDto>()
        val emptyJsonDto = gson.toJson(emptyDto)

        val response = MockResponse()
        response.setBody(emptyJsonDto)
        response.setResponseCode(400)
        server.enqueue(response)

        val result = remoteDataService.getMovies(1,SortBy.Popular)
        server.takeRequest()

        assertEquals(result.data?.results,null)
        assertEquals(result.code,400)
        assertEquals(result.status,Result.Status.ERROR)
    }

    @Test
    fun `Should return a list of movies`() = runTest {
        val modelJson = gson.toJson(DataUtil.responseDto)

        val response = MockResponse()
        response.setBody(modelJson)
        response.setResponseCode(200)

        server.enqueue(response)

        val result = remoteDataService.getMovies(1,SortBy.Popular)
        server.takeRequest()

        val bodyJson = gson.toJson(result.data)

        assertEquals(bodyJson,modelJson)
        assertEquals(result.code,200)
        assertThat(result.data).isNotNull()
        assertThat(result.data).isInstanceOf(ResponseDto::class.java)
        assertThat(result.data?.results).isNotEmpty()
        assertThat(result.data?.results?.get(0)).isInstanceOf(MovieDto::class.java)
    }

    @Test
    fun `Should map movie dto to movie entity`(){
        val movieEntity = MovieMapper.toMovieEntity(DataUtil.movieDto)
        assertEquals(DataUtil.movieEntity,movieEntity)
    }

    @Test
    fun `Should map movie entity to movie dto`(){
        val movieDto = MovieMapper.toMovieDto(DataUtil.movieEntity)
        assertEquals(DataUtil.movieDto,movieDto)
    }

    @Test
    fun `Remote service should be a children of network request`(){
        assertThat(remoteDataService).isNotNull()
        assertThat(remoteDataService).isInstanceOf(NetworkRequest::class.java)
    }

    @Test
    fun `Remote service should return a result instance`() = runTest{
        val modelJson = gson.toJson(DataUtil.responseDto)

        val response = MockResponse()
        response.setBody(modelJson)
        response.setResponseCode(200)

        server.enqueue(response)

        val result = remoteDataService.getMovies(1, SortBy.Popular)
        server.takeRequest()

        assertThat(result).isInstanceOf(Result::class.java)
    }





    @After
    fun after(){
        server.shutdown()
    }

}