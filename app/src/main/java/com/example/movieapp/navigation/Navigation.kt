package com.example.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.MovieViewModel
import com.example.movieapp.MovieViewModelFactory
import com.example.movieapp.data.MovieDataBase
import com.example.movieapp.repositories.MovieRepository
import com.example.movieapp.screens.*

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route){
            HomeScreen(navController = navController)
        }

        composable(Screen.FavoriteScreen.route) {
            FavoriteScreen(navController = navController)
        }

        composable(Screen.AddMovieScreen.route) {
            AddMovieScreen(navController = navController)
        }

        // build a route like: root/detail-screen/id=34
        composable(
            Screen.DetailScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) {type = NavType.StringType})
        ) {
            DetailScreen(navController = navController, movieId = it.arguments?.getInt(
                DETAIL_ARGUMENT_KEY))   // get the argument from navhost that will be passed
        }
    }
}