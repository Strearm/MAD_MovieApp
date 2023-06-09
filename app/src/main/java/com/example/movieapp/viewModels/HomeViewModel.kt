package com.example.movieapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
import com.example.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository): ViewModel() {
    private val _movieList = MutableStateFlow(listOf<Movie>())
    val movieList: StateFlow<List<Movie>> = _movieList.asStateFlow()
    val initList: List<Movie> = getMovies()
    init {
        viewModelScope.launch {
            repository.getAll().collect{ movies ->
                if (movies.isEmpty()){
                    for(movie in initList){
                        addMovie(movie)
                    }
                }
                if(movies != null){
                    _movieList.value = movies
                }
            }
        }
    }

    suspend fun addMovie(movie: Movie){
        repository.add(movie)
    }

    suspend fun toggleFavourite(movie: Movie){
        movie.isFavourite = !movie.isFavourite
        repository.update(movie)
    }

    suspend fun deleteMovie(movie: Movie){
        repository.delete(movie)
    }
}