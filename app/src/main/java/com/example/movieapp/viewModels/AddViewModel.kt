package com.example.movieapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.Movie
import com.example.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddViewModel (private val repository: MovieRepository): ViewModel() {

    private val _movieList = MutableStateFlow(listOf<Movie>())
    val movieList: StateFlow<List<Movie>> = _movieList.asStateFlow()
    init {
        viewModelScope.launch {
            repository.getAll().collect{ movies ->
                if(movies != null){
                    _movieList.value = movies
                }
            }
        }
    }

    fun validateInputNotEmpty(text: String): Boolean{
        return text.isEmpty()
    }

    fun validateInputFloat(text: String): Boolean{
        return if(text.isEmpty()){
            true
        } else{
            text.toFloatOrNull() == null
        }
    }

    suspend fun addMovie(movie: Movie){
        repository.add(movie)
    }
}