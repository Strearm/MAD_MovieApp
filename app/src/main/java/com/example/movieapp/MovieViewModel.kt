package com.example.movieapp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.Genre
import com.example.movieapp.models.ListItemSelectable
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
import com.example.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository): ViewModel () {
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

    /////ACHTUNG BAUSTELLE/////ACHTUNG BAUSTELLE/////ACHTUNG BAUSTELLE/////ACHTUNG BAUSTELLE/////
    fun movieFilter(id: Int): Movie{
       return _movieList.value.filter{it.id == id}[0]
    }
    /////BAUSTELLE ENDE/////BAUSTELLE ENDE/////BAUSTELLE ENDE/////BAUSTELLE ENDE/////
    suspend fun toggleFavourite(movie: Movie){
        movie.isFavourite != movie.isFavourite
        repository.update(movie)
    }

    fun favouriteMoviesList(): List<Movie>{
        return movieList.value.filter { it.isFavourite  }
    }

    suspend fun addMovie(movie: Movie){
        repository.add(movie)
    }

    suspend fun deleteMovie(movie: Movie){
        repository.delete(movie)
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
}