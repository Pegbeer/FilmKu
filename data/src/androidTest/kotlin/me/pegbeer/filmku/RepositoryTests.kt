package me.pegbeer.filmku

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import createInMemoryDatabase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import me.pegbeer.filmku.local.DatabaseService
import me.pegbeer.filmku.local.LocalDataService
import me.pegbeer.filmku.remote.ApiService
import me.pegbeer.filmku.remote.NetworkService
import me.pegbeer.filmku.remote.RemoteDataService
import me.pegbeer.filmku.repository.Repository
import me.pegbeer.filmku.repository.RepositoryImpl
import me.pegbeer.filmku.util.DataUtil
import me.pegbeer.filmku.util.Result
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class RepositoryTests {

    private lateinit var server: MockWebServer
    private lateinit var repository: Repository
    private lateinit var localDataService: LocalDataService
    private lateinit var remoteDataService: RemoteDataService
    private lateinit var apiService: ApiService

    private val gson = Gson()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp(){
        server = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
        localDataService = DatabaseService(createInMemoryDatabase())
        remoteDataService = NetworkService(apiService)
        repository = RepositoryImpl(localDataService,remoteDataService)
    }

    @Test
    fun shouldSaveGenresSuccessfully() = runTest{
        val responseJson = gson.toJson(DataUtil.genreResponseDto)
        val response = MockResponse()
        response.setBody(responseJson)
        response.setResponseCode(200)
        server.enqueue(response)

        val result = async {
            repository.saveGenres()
        }
        val request = server.takeRequest(300L,TimeUnit.MILLISECONDS)
        val data = if(request == null) result.await() else null

        assertThat(data).isNotNull()
        assertThat(data).isInstanceOf(Result::class.java)
        assertThat(data?.data).isNotEmpty()
        assertThat(data?.data).isEqualTo(DataUtil.genresEntities)
    }


    @Test
    fun getNowPlayingMoviesReturnsFlowOfPagingData() = runTest{
        val responseBodyJson = gson.toJson(DataUtil.responseDto)
        val response = MockResponse()
        response.setBody(responseBodyJson)
        response.setResponseCode(200)
        server.enqueue(response)

        val result = async {
            repository.getNowPlayingMovies(1).first()
        }
        val request = server.takeRequest(300L,TimeUnit.MILLISECONDS)
        val data = if(request == null){
            result.await()
        } else {
            null
        }

        assertThat(data).isNotNull()
        assertThat(data).isInstanceOf(PagingData::class.java)
    }

    @Test
    fun shouldReturnAMovieDetailFromNetwork() = runTest {
        val responseJson = gson.toJson(DataUtil.movieDetailDto)
        val response = MockResponse()
        response.setBody(responseJson)
        response.setResponseCode(200)

        server.enqueue(response)

        val result = async{ remoteDataService.getMovieDetails(1L) }

        val request = server.takeRequest(100L, TimeUnit.MILLISECONDS)
        val data = if(request == null) result.await() else null

        assertThat(data).isNotNull()
        assertThat(data?.status).isEqualTo(Result.Status.SUCCESS)
        assertThat(data?.data).isNotNull()
        assertThat(data?.data).isEqualTo(DataUtil.movieDetailDto)
    }



    fun shouldReturnAMovieDetail() = runTest {
        server.enqueue(MockResponse().setBody(gson.toJson(DataUtil.genreResponseDto)).setResponseCode(200))
        server.enqueue(MockResponse().setBody(gson.toJson(DataUtil.responseDto)).setResponseCode(200))
        server.enqueue(MockResponse().setBody(gson.toJson(DataUtil.movieDetailDto)).setResponseCode(200))


        val genresData = runBlocking { repository.saveGenres() }
        val pagingData = runBlocking { repository.getNowPlayingMovies(1).first() }
        val movieDetailData = runBlocking { repository.getMovieDetail(1L) }


        val genresRequest = server.takeRequest(100L, TimeUnit.MILLISECONDS)
        val pagingRequest = server.takeRequest(100L, TimeUnit.MILLISECONDS)
        val movieDetailRequest = server.takeRequest(100L, TimeUnit.MILLISECONDS)


        assertThat(genresRequest).isNotNull()
        assertThat(pagingRequest).isNotNull()
        assertThat(movieDetailRequest).isNotNull()

        assertThat(genresData).isNotNull()
        assertThat(genresData.data).isNotNull()
        assertThat(genresData.data).isNotEmpty()
        assertThat(genresData.status).isEqualTo(Result.Status.SUCCESS)
        assertThat(genresData.data).isEqualTo(DataUtil.genresEntities)

        assertThat(pagingData).isNotNull()
        assertThat(pagingData).isInstanceOf(PagingData::class.java)

        assertThat(movieDetailData).isNotNull()
        assertThat(movieDetailData.data).isNotNull()
        assertThat(movieDetailData.status).isEqualTo(Result.Status.SUCCESS)
        assertThat(movieDetailData.data?.cast).isNotEmpty()
        assertThat(movieDetailData.data?.genres).isNotEmpty()
    }




    @After
    fun onDispose(){
        server.shutdown()
    }


}