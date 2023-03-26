package com.example.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.MovieRow
import com.example.movieapp.SimpleMenuBar
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies

@Composable
fun DetailScreen( navController: NavController,
                  id: String,
){
    val thisMovie = getMovie(id = id)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            SimpleMenuBar(thisMovie!!.title, navController)
            MovieRow(thisMovie){ run {} }
            ImageRow(thisMovie)
        }
    }
}
@Composable
fun ImageRow(movie: Movie){
    Row {
        LazyRow{
            items(movie.images){image ->
                Card(modifier = Modifier.padding(5.dp).size(300.dp,200.dp),
                    shape = RoundedCornerShape(corner = CornerSize(5.dp)),
                    elevation = 5.dp
                ) {
                    AsyncImage(model = image,
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop)
                }
            }
        }

    }
}
private fun getMovie(movieList: List<Movie> = getMovies(), id: String): Movie? {
    return movieList.find { it.id == id }
}