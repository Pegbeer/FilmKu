package me.pegbeer.filmku

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dataModule
import dto.MovieDto
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import remote.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataModuleTests : KoinTest {

    private lateinit var server: MockWebServer
    private lateinit var api:ApiService


    private val type = TypeToken.getParameterized(List::class.java,MovieDto::class.java).type
    private val gson = GsonBuilder().create()
    private val model = MovieDto(
        listOf(1),
        1L,
        "Lorem ipsum",
        "google.com",
        "Batman",
        9.5,
        "en-US"
    )

    private val listModel = listOf(
        MovieDto(
            listOf(878,12),
            693134L,
            "Follow the mythic journey of Paul Atreides as he unites with Chani and the Fremen while on a path of revenge against the conspirators who destroyed his family. Facing a choice between the love of his life and the fate of the known universe, Paul endeavors to prevent a terrible future only he can foresee.",
            "/1pdfLvkbY9ohJlCjQH2CZjjYVvJ.jpg",
            "Dune: Part Two",
            8.292,
            "en"
        )
    )

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(dataModule)
    }

    @Before
    fun setUp(){
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }



    @Test
    fun `Should serialize and deserialize dto class`(){
        val jsonString = gson.toJson(model)
        val deserializedJson = gson.fromJson(jsonString,MovieDto::class.java)

        assertEquals(model,deserializedJson)
    }

    @Test
    fun `Should return an empty list on error`() = runTest{
        val dto = emptyList<MovieDto>()
        val jsonDto = gson.toJson(dto)

        val response = MockResponse()
        response.setBody(jsonDto)
        response.setResponseCode(404)
        server.enqueue(response)

        val data = api.getNowPlayingMovies()
        server.takeRequest()

        val bodyJson = gson.toJson(data.body().orEmpty())

        assertEquals(data.body().orEmpty(),dto)
        assertEquals(bodyJson,jsonDto)
        assertEquals(data.code(),404)
    }

    @Test
    fun `Should return a list of movies`() = runTest {
        val listModelJson = gson.toJson(listModel,type)

        val response = MockResponse()
        response.setBody(listModelJson)
        response.setResponseCode(200)

        server.enqueue(response)

        val data = api.getNowPlayingMovies()
        server.takeRequest()

        val bodyJson = gson.toJson(data.body().orEmpty())

        assertEquals(listModelJson,bodyJson)
        assertEquals(data.code(),200)
    }



    @After
    fun after(){
        server.shutdown()
    }

}