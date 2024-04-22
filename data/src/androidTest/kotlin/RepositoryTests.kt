import androidx.paging.PagingData
import com.google.gson.Gson
import dto.MovieDto
import dto.ResponseDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import local.DatabaseService
import local.LocalDataService
import local.entity.MovieEntity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import remote.ApiService
import remote.NetworkService
import remote.RemoteDataService
import repository.Repository
import repository.RepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.common.truth.Truth.*

class RepositoryTests : KoinTest {

    private lateinit var server:MockWebServer
    private lateinit var repository: Repository
    private lateinit var localDataService: LocalDataService
    private lateinit var remoteDataService: RemoteDataService
    private lateinit var apiService: ApiService

    private val gson = Gson()

    @Before
    fun setUp(){
        server = MockWebServer()
        server.start(1025)
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
    fun getNowPlayingMoviesReturnsFlowOfPagingData() = runBlocking{
        val movieDto = MovieDto(id = 1L,"","","",1.0, listOf(1L),"")
        val responseDto = ResponseDto(1, listOf(movieDto),1,1)
        val responseBodyJson = gson.toJson(responseDto)
        val response = MockResponse()
        response.setBody(responseBodyJson)
        response.setResponseCode(200)
        server.enqueue(response)

        val result = repository.getNowPlayingMovies(1).first()
        server.takeRequest()

        assertThat(result).isInstanceOf(PagingData::class.java)
    }

    @After
    fun onDispose(){
        server.shutdown()
    }


}