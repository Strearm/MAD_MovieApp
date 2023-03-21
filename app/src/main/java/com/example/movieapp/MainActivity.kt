package com.example.movieapp

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
import com.example.movieapp.ui.theme.MovieAppTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        MenuBar()
                        Greeting()
                        MovieList()
                    }
                }
            }
        }
    }
}
//standard Modifier!
val standardMod: Modifier = Modifier.fillMaxWidth().padding(5.dp)

@Composable
fun MenuBar(){
    var expanded by remember{
        mutableStateOf(false)
    }
    TopAppBar(modifier = Modifier
        .fillMaxWidth(),
    ) {
        Text(text = "Menu")
        Spacer(Modifier.weight(1f))
        Box(){
            IconButton(onClick = {expanded = true}) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Open Menu")
            }
            DropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false },
            ){
                DropdownMenuItem(onClick = { expanded = false }
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "favourite Heart" )
                    Text(text = "Favourites")
                }
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie){
    var rotation by remember {
        mutableStateOf(0f)
    }
    var favourite by remember{
        mutableStateOf("notLiked")
    }
    var show by remember{
        mutableStateOf(false)
    }
    Card(modifier = standardMod,
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 5.dp
        ) {
        Column {
            Box( modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
            ) {
                AsyncImage( modifier = Modifier.fillMaxWidth(),
                    model = movie.images[0],
                    contentDescription = "Movie Image",
                    contentScale = ContentScale.Crop
                )
                Icon(tint = Color.Red,
                    imageVector = if(favourite == "notLiked") Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                    contentDescription = "Favourite",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(40.dp)
                        .padding(5.dp)
                        .clickable(
                            onClickLabel = "favourite",
                            onClick = {
                                favourite = if (favourite == "notLiked") "liked" else "notLiked"
                            }
                        )
                )
            }
            Row(modifier = standardMod,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = movie.title)
                Icon(imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Arrow Up",
                    modifier = Modifier
                        .clickable(
                            onClickLabel = "clicked",
                            onClick = {
                                rotation = if (rotation == 180f) 0f else 180f
                                show = show != true
                            }
                        )
                        .rotate(rotation)
                    )
            }
            AnimatedVisibility(visible = show) {
                ShowMovieInformation(movie = movie)
            }
        }
    }
}

@Composable
fun ShowMovieInformation(movie: Movie){
    Column (modifier = standardMod) {
        Text(text =  "Director: ${movie.director}")
        Text(text = "Release: ${movie.year}")
        Text(text = "Genre: ${movie.genre}")
        Text(text = "Actors: ${movie.actors}")
        Text(text = "Rating: ${movie.rating}")
        Divider(thickness = 1.dp,
            color = Color.Black,
            modifier = Modifier.padding(0.dp,5.dp)
        )
        Text(text = "Plot: ${movie.plot}")
    }
}

@Composable
fun MovieList(movies: List<Movie> = getMovies()){
    Column {
        LazyColumn{
            items(movies) {
                movie -> MovieRow(movie)
            }
        }
    }
}

@Composable
fun Greeting() {
    Column(modifier = Modifier.padding(16.dp)) {
        var name by remember{
            mutableStateOf("")
        }
        Text(text = "Hello ${name}!")
        OutlinedTextField(value = name,
            onValueChange = {name = it},
            label = { Text(text = "Name")}
        )
    }
}
