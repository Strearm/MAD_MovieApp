package com.example.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieapp.MovieViewModel
import com.example.movieapp.data.MovieDataBase
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.viewModelFactories.DetailViewModelFactory
import com.example.movieapp.viewModelFactories.HomeViewModelFactory
import com.example.movieapp.viewModels.DetailViewModel
import com.example.movieapp.viewModels.HomeViewModel
import com.example.movieapp.widgets.HorizontalScrollableImageView
import com.example.movieapp.widgets.MovieRow
import com.example.movieapp.widgets.SimpleTopAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun DetailScreen(navController: NavController, movieId:Int? ){

    val db = MovieDataBase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = DetailViewModelFactory(repository = repository)
    val movieViewModel: DetailViewModel = viewModel(factory = factory)

    val coroutineScope = rememberCoroutineScope()

    movieId?.let {
        var movie: Movie = Movie(title = "1", actors = "1", plot = "1", director = "1", year = "1", genre = "[ACTION]", images = listOf(""),rating = 1f)
        coroutineScope.launch {
            movie = movieViewModel.movieFilter(movieId)
        }

        // needed for show/hide snackbar
        val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`

        Scaffold(scaffoldState = scaffoldState, // attaching `scaffoldState` to the `Scaffold`
            topBar = {
                SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                    Text(text = movie.title)
                }
            },
        ) { padding ->
            MainContent(Modifier.padding(padding), movie, movieViewModel = movieViewModel, navController = navController,
            coroutineScope = coroutineScope)
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, movie: Movie, movieViewModel: DetailViewModel,
                navController: NavController, coroutineScope: CoroutineScope)
{
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            MovieRow(
                movie = movie,
                onItemClick = { movieId ->
                    navController.navigate(route = Screen.DetailScreen.withId(movieId))
                },
                onFavClick = {
                    coroutineScope.launch{
                        movieViewModel.updateFavorite(movie)}
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Divider()

            Text(text = "Movie Images", style = MaterialTheme.typography.h5)

            HorizontalScrollableImageView(movie = movie)
        }
    }
}