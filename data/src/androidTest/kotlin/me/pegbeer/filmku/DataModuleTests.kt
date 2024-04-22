import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import me.pegbeer.filmku.local.Database
import me.pegbeer.filmku.local.DatabaseService
import me.pegbeer.filmku.local.LocalDataService
import me.pegbeer.filmku.local.entity.MovieEntity
import me.pegbeer.filmku.remote.ApiService
import me.pegbeer.filmku.remote.NetworkService
import me.pegbeer.filmku.remote.RemoteDataService
import me.pegbeer.filmku.repository.Repository
import me.pegbeer.filmku.repository.RepositoryImpl
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals


@RunWith(AndroidJUnit4::class)
class DataModuleTests  {

    private lateinit var server: MockWebServer
    private lateinit var repository: Repository
    private lateinit var localDataService: LocalDataService
    private lateinit var remoteDataService: RemoteDataService
    private lateinit var apiService: ApiService


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
        assertEquals(result.size, 1)
    }



    @After
    fun dispose(){
        server.shutdown()
    }

    private fun createInMemoryDatabase(): Database {
        return Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            Database::class.java
        ).build()
    }
}