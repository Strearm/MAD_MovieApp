package com.example.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapp.MovieRow
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies

@Composable
fun HomeScreen(navController: NavController){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            MenuBar(menuText = "Movie App", navController = navController)
            Greeting()
            MovieList(navController = navController)
        }
    }
}
@Composable
fun MenuBar(menuText: String = "Menu", navController: NavController) {
    var expanded by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(text = menuText)
        Spacer(Modifier.weight(1f))
        Box() {
            IconButton(onClick = { expanded = true }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Open Menu")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                DropdownMenuItem(onClick = {
                    navController.navigate("favourites")
                }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Menu Favourites"
                    )
                    Text(text = "Favourites")
                } //DropdownMenuItem end
            } //DropdownMenu end
        } //Box end
    } //TopAppBar end
}
@Composable
private fun Greeting() {
    Column(modifier = Modifier.padding(16.dp)) {
        var name by remember{
            mutableStateOf("")
        }
        Text(text = "Hello ${name}!")
        OutlinedTextField(value = name,
            onValueChange = {name = it},
            label = { Text(text = "Name") }
        )
    }
}
@Composable
private fun MovieList(movies: List<Movie> = getMovies(), navController: NavController) {
    Column {
        LazyColumn {
            items(movies) { movie ->
                MovieRow(movie = movie) { movieID ->
                    navController.navigate(
                        route = "detailscreen/$movieID"
                    )
                }
            }
        }
    }
}