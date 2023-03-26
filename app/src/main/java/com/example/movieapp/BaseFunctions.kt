package com.example.movieapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.models.Movie

@Composable
fun SimpleMenuBar(menuText: String = "Menu", navController: NavController) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        BackButton(navController = navController)
        Text(text = menuText)
        Spacer(Modifier.weight(1f))
        MenuButton(navController = navController)
    } //TopAppBar end
}
@Composable
fun MovieRow(movie: Movie, onItemClick: (String) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 5.dp
    ) {
        Column {
            DrawMovieImage(movie = movie, onItemClick)
            DrawDetailRow(movie = movie)
        }
    }
}
@Composable
private fun BackButton(navController: NavController){
    IconButton(onClick = {
        navController.popBackStack()
        //if we want our BackButton to return to the homescreen, uncomment the following lines and comment the line above
//        navController.navigate(Screen.Home.route) {
//            popUpTo(Screen.Home.route) {
//                inclusive = true
//            }
//        }
    }
    ) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "backButton")
    }// IconButton end
}
@Composable
fun MenuButton(navController: NavController){
    var expanded by remember {
        mutableStateOf(false)
    }
    Box() {
        IconButton(onClick = { expanded = true }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Open Menu")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(onClick = {
                navController.navigate(Screen.Favourite.route)
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
}
@Composable
private fun DrawMovieImage(movie: Movie, onItemClick: (String) -> Unit = {}){
    var favourite by remember {
        mutableStateOf("notLiked")
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(movie.id) },
            model = movie.images[0],
            contentDescription = "Movie Image",
            contentScale = ContentScale.Crop
        )
        Icon(tint = Color.Red,
            imageVector = if (favourite == "notLiked") Icons.Default.FavoriteBorder else Icons.Default.Favorite,
            contentDescription = "Favourite",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(40.dp)
                .padding(5.dp)
                .clickable(
                    onClickLabel = "favourite",
                    onClick = { favourite = if (favourite == "notLiked") "liked" else "notLiked" }
                )
        )
    }
}
@Composable
private fun DrawDetailRow(movie: Movie){
    var rotation by remember {
        mutableStateOf(0f)
    }
    var show by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
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
@Composable
private fun ShowMovieInformation(movie: Movie) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
    ) {
        Text(text = "Director: ${movie.director}")
        Text(text = "Release: ${movie.year}")
        Text(text = "Genre: ${movie.genre}")
        Text(text = "Actors: ${movie.actors}")
        Text(text = "Rating: ${movie.rating}")
        Divider(thickness = 1.dp, color = Color.Black, modifier = Modifier.padding(0.dp, 5.dp))
        Text(text = "Plot: ${movie.plot}")
    }
}

