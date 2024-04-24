package me.pegbeer.filmku.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.pegbeer.filmku.model.MovieDetail
import me.pegbeer.filmku.repository.Repository
import me.pegbeer.filmku.util.Result

class DetailViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _movie = MutableStateFlow<Result<MovieDetail>>(Result.loading())
    val movie = _movie.asStateFlow()

    fun getMovieDetails(id:Long){
        viewModelScope.launch {
            _movie.value = repository.getMovieDetail(id)
        }
    }
}