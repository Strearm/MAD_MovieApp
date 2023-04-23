package com.example.movieapp.viewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.MovieViewModel
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.viewModels.FavoritesViewModel

class FavoritesViewModelFactory (private val repository: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoritesViewModel::class.java)){
            return FavoritesViewModel(repository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}