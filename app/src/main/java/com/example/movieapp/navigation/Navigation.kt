package com.example.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.MOVIE_KEY
import com.example.movieapp.Screen
import com.example.movieapp.screens.DetailScreen
import com.example.movieapp.screens.FavouriteScreen
import com.example.movieapp.screens.HomeScreen

@Composable
fun MyNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Favourite.route) { FavouriteScreen(navController = navController) }
        composable(route = Screen.Home.route) { HomeScreen(navController = navController) }
        composable(route = Screen.Detail.route,
            arguments = listOf( navArgument(MOVIE_KEY) { type = NavType.StringType })
        ) {
            DetailScreen(
                navController = navController,
                id = it.arguments?.getString(MOVIE_KEY).toString()
            )
        }
    }
}