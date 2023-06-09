package com.example.movieapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.Movie
import com.example.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: MovieRepository): ViewModel() {
    private val _movieList = MutableStateFlow(listOf<Movie>())
    val movieList: StateFlow<List<Movie>> = _movieList.asStateFlow()
    init {
        viewModelScope.launch {
            repository.getFavourites().collect{ movies ->
                if(movies != null){
                    _movieList.value = movies
                }
            }
        }
    }

    suspend fun updateFavorite(movie: Movie){
        movie.isFavourite = !movie.isFavourite
        repository.update(movie)
    }

}