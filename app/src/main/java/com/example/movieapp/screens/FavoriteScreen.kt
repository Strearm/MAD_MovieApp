package com.example.movieapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieapp.MovieViewModel
import com.example.movieapp.MovieViewModelFactory
import com.example.movieapp.data.MovieDataBase
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.viewModelFactories.FavoritesViewModelFactory
import com.example.movieapp.viewModels.FavoritesViewModel
import com.example.movieapp.widgets.MovieRow
import com.example.movieapp.widgets.SimpleTopAppBar
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(navController: NavController){
    val db = MovieDataBase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = FavoritesViewModelFactory(repository = repository)
    val movieViewModel: FavoritesViewModel = viewModel(factory = factory)

    val movieListState by movieViewModel.movieList.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    Scaffold(topBar = {
        SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
            Text(text = "My Favorite Movies")
        }
    }){ padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyColumn {
                items(movieListState){ movie ->
                    MovieRow(
                        movie = movie,
                        onItemClick = { movieId ->
                            navController.navigate(route = Screen.DetailScreen.withId(movieId))
                        },
                        onFavClick = {
                            coroutineScope.launch {
                                movieViewModel.updateFavorite(movie)}
                        }
                    )
                }
            }
        }
    }
}