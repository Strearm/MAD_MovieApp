package com.example.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.MovieViewModel
import com.example.movieapp.MovieViewModelFactory
import com.example.movieapp.data.MovieDataBase
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.viewModelFactories.HomeViewModelFactory
import com.example.movieapp.viewModels.HomeViewModel
import com.example.movieapp.widgets.HomeTopAppBar
import com.example.movieapp.widgets.MovieRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController = rememberNavController()){
    val db = MovieDataBase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = HomeViewModelFactory(repository = repository)
    val movieViewModel: HomeViewModel = viewModel(factory = factory)

    val coroutineScope = rememberCoroutineScope()

    val movieListState by movieViewModel.movieList.collectAsState()


    Scaffold(topBar = {
        HomeTopAppBar(
            title = "Home",
            menuContent = {
                DropdownMenuItem(onClick = { navController.navigate(Screen.AddMovieScreen.route) }) {
                    Row {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Add Movie", modifier = Modifier.padding(4.dp))
                        Text(text = "Add Movie", modifier = Modifier
                            .width(100.dp)
                            .padding(4.dp))
                    }
                }
                DropdownMenuItem(onClick = { navController.navigate(Screen.FavoriteScreen.route) }) {
                    Row {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites", modifier = Modifier.padding(4.dp))
                        Text(text = "Favorites", modifier = Modifier
                            .width(100.dp)
                            .padding(4.dp))
                    }
                }
            }
        )
    }) { padding ->
        MovieList(modifier = Modifier.padding(padding), navController = navController, movieViewModel = movieViewModel,movieListState = movieListState, coroutineScope = coroutineScope )
    }
}

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    navController: NavController,
    movieViewModel: HomeViewModel,
    movieListState: List<Movie>,
    coroutineScope: CoroutineScope
) {


    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(movieListState) { movie ->
            MovieRow(
                movie = movie,
                onItemClick = { movieId ->
                    navController.navigate(Screen.DetailScreen.withId(movieId))
                },
                onFavClick = {
                    coroutineScope.launch{
                        movieViewModel.toggleFavourite(movie)}
                },
                onDelClick = {
                    coroutineScope.launch {
                        movieViewModel.deleteMovie(movie)}
                }
            )
        }
    }
}


