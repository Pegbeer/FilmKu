import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import local.Database
import local.entity.MovieEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import repository.Repository
import repository.RepositoryImpl
import kotlin.test.assertEquals


class DataModuleTests : KoinTest {

    private lateinit var database: Database
    private lateinit var repository: Repository

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(dataModule)
    }

    @Before
    fun setUp(){
        database = createInMemoryDatabase()
        repository = RepositoryImpl(database)
    }

    @Test
    fun shouldSaveAMovieSuccessfully(){
        val movie = MovieEntity(
            1L,
            listOf(1,2),
            "",
            "",
            "",
            1.0,
            ""
        )

        val result = runBlocking {
            database.movieDao.saveMovie(movie)
        }

        assertEquals(movie.id, result)
    }

    @Test
    fun shouldReturnAllTheFavoritesMovies(){
        runBlocking {
            database.movieDao.saveMovie(
                MovieEntity(
                    1L,
                    listOf(1,2),
                    "",
                    "",
                    "",
                    1.0,
                    ""
                )
            )
            database.movieDao.saveMovie(
                MovieEntity(
                    2L,
                    listOf(3,4),
                    "",
                    "",
                    "",
                    2.0,
                    ""
                )
            )
        }

        val result = runBlocking {
            database.movieDao.getAll()
        }

        assert(result.isNotEmpty())
        assertEquals(result.size, 2)
    }



    @After
    fun dispose(){
        database.close()
    }

    private fun createInMemoryDatabase(): Database {
        return Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            Database::class.java
        ).build()
    }
}