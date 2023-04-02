package com.example.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
import com.example.movieapp.widgets.HorizontalScrollableImageView
import com.example.movieapp.widgets.MovieRow
import com.example.movieapp.widgets.SimpleTopAppBar

fun filterMovie(movieId: String): Movie {
    return getMovies().filter { it.id == movieId}[0]
}
@Composable
fun DetailScreen(
    navController: NavController,
    movieId:String?){

    movieId?.let {
        val movie = filterMovie(movieId = movieId)

        // needed for show/hide snackbar
        val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`

        Scaffold(scaffoldState = scaffoldState, // attaching `scaffoldState` to the `Scaffold`
            topBar = {
                SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                    Text(text = movie.title)
                }
            },
        ) { padding ->
            MainContent(Modifier.padding(padding), movie)
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, movie: Movie) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            MovieRow(movie = movie)

            Spacer(modifier = Modifier.height(8.dp))

            Divider()

            Text(text = "Movie Images", style = MaterialTheme.typography.h5)

            HorizontalScrollableImageView(movie = movie)
        }
    }
}