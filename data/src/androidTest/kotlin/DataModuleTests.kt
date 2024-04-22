import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import local.Database
import local.DatabaseService
import local.LocalDataService
import local.entity.MovieEntity
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
import kotlin.test.assertEquals


class DataModuleTests : KoinTest {

    private lateinit var server: MockWebServer
    private lateinit var repository: Repository
    private lateinit var localDataService: LocalDataService
    private lateinit var remoteDataService: RemoteDataService
    private lateinit var apiService: ApiService

    private val gson = Gson()


    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(dataModule)
    }

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
    fun shouldSaveAMovieSuccessfully(){
        val movie = MovieEntity(
            1L,
            "",
            "",
            "",
            "",
            1.0,
            listOf(1L)
        )

        val result = runBlocking {
            localDataService.insertMovie(movie)
        }

        assertEquals(movie.id, result)
    }

    @Test
    fun shouldReturnAllTheFavoritesMovies(){
        runBlocking {
            localDataService.insertMovie(
                MovieEntity(
                    1L,
                    "",
                    "",
                    "",
                    "",
                    1.0,
                    listOf(1L)
                )
            )
            localDataService.insertMovie(
                MovieEntity(
                    1L,
                    "",
                    "",
                    "",
                    "",
                    1.0,
                    listOf(1L)
                )
            )
        }

        val result = runBlocking {
            localDataService.getAllMovies()
        }

        assert(result.isNotEmpty())
        assertEquals(result.size, 2)
    }



    @After
    fun dispose(){
        stopKoin()
        server.shutdown()
    }

    private fun createInMemoryDatabase(): Database {
        return Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            Database::class.java
        ).build()
    }
}