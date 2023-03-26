package com.example.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieapp.MovieRow
import com.example.movieapp.SimpleMenuBar
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getFavourites

@Composable
fun FavouriteScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            SimpleMenuBar(menuText = "Favourites", navController = navController)
            FavouriteList(navController = navController)
        }
    }
}

@Composable
private fun FavouriteList(favourites: List<Movie> = getFavourites(), navController: NavController) {
    Column {
        LazyColumn {
            items(favourites) { movie ->
                MovieRow(movie = movie) { movieID ->
                    navController.navigate(
                        route = "detailscreen/$movieID"
                    )
                }
            }
        }
    }
}





