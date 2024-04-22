package me.pegbeer.filmku

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dataModule
import dto.MovieDto
import dto.ResponseDto
import kotlinx.coroutines.test.runTest
import local.entity.MovieEntity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import remote.ApiService
import remote.NetworkService
import remote.RemoteDataService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataModuleTests : KoinTest {

    private lateinit var server: MockWebServer
    private lateinit var apiService:ApiService
    private lateinit var remoteDataService: RemoteDataService


    private val type = TypeToken.getParameterized(List::class.java,MovieDto::class.java).type
    private val gson = GsonBuilder().create()
    private val model = MovieEntity(
        1L,
        "",
        "",
        "",
        "",
        1.0,
        listOf(1L)
    )

    private val listModel = listOf(
        MovieDto(
            1L,
            "",
            "",
            "",
            1.0,
            listOf(1L),
            ""
        )
    )

    private val modelDto = ResponseDto(1, listModel,1,1)

    @Before
    fun setUp(){
        server = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
        remoteDataService = NetworkService(apiService)
    }



    @Test
    fun `Should serialize and deserialize dto class`(){
        val jsonString = gson.toJson(model)
        val deserializedJson = gson.fromJson(jsonString,MovieDto::class.java)

        assertEquals(model,deserializedJson)
    }

    @Test
    fun `Should return an empty list on error`() = runTest{
        val emptyDto = emptyList<MovieDto>()
        val emptyJsonDto = gson.toJson(emptyDto)
        val jsonDto = gson.toJson(modelDto)

        val response = MockResponse()
        response.setBody(emptyJsonDto)
        response.setResponseCode(400)
        server.enqueue(response)

        val data = remoteDataService.getNowPlayingMovies()
        server.takeRequest()

        val bodyJson = gson.toJson(data.data)

        assertEquals(data.data?.results,emptyDto)
        assertEquals(bodyJson,jsonDto)
        assertEquals(data.code,400)
    }

    @Test
    fun `Should return a list of movies`() = runTest {


        val modelJson = gson.toJson(modelDto)

        val response = MockResponse()
        response.setBody(modelJson)
        response.setResponseCode(200)

        server.enqueue(response)

        val result = remoteDataService.getNowPlayingMovies()
        server.takeRequest()

        val bodyJson = gson.toJson(result.data)

        assertEquals(bodyJson,modelJson)
        assertEquals(result.code,200)
    }



    @After
    fun after(){
        stopKoin()
        server.shutdown()
    }

}