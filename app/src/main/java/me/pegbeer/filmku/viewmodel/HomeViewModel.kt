package me.pegbeer.filmku.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import me.pegbeer.filmku.local.entity.GenreEntity
import me.pegbeer.filmku.local.entity.MovieWithGenres
import me.pegbeer.filmku.repository.Repository
import me.pegbeer.filmku.util.Result

class HomeViewModel(
    private val repository:Repository
) : ViewModel() {

    val nowPlayingMovies = repository.getNowPlayingMovies()
    val popularMovies = repository.getPopularMovies()

}