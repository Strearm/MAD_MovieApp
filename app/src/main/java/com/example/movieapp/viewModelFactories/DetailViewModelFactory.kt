package com.example.movieapp.viewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.viewModels.DetailViewModel
import com.example.movieapp.viewModels.FavoritesViewModel

class DetailViewModelFactory (private val repository: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(repository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}