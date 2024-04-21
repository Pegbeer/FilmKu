package repository

import dto.MovieDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import local.Database
import mapper.MovieMapper

class RepositoryImpl(
    private val database: Database
) : Repository {
    override suspend fun save(movie: MovieDto):Long {
        return withContext(Dispatchers.IO){
            database.movieDao.saveMovie(MovieMapper.toMovieEntity(movie))
        }
    }
}