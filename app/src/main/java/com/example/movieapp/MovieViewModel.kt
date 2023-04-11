package com.example.movieapp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieapp.models.Genre
import com.example.movieapp.models.ListItemSelectable
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies

class MovieViewModel: ViewModel () {
    private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie>
        get() = _movieList


    fun movieFilter(id: String): Movie{
       return _movieList.filter{it.id == id}[0]
    }
    fun toggleFavourite(movie: Movie){
        movieList.find { it.id == movie.id }?.let { _movie ->
            _movie.isFavorite = !_movie.isFavorite
        }
    }

    fun favouriteMoviesList(): List<Movie>{
        return movieList.filter { it.isFavorite  }
    }

    fun addMovie(movie: Movie){
        _movieList.add(movie)
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