package com.example.movieapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.movieapp.models.Movie
import com.example.movieapp.repositories.MovieRepository

class DetailViewModel (private val repository: MovieRepository): ViewModel()  {

    suspend fun movieFilter(id: Int): Movie {
        return repository.getById(id)
    }

    suspend fun updateFavorite(movie: Movie){
        movie.isFavourite = !movie.isFavourite
        repository.update(movie)
    }
}