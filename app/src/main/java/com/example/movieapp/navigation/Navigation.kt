package com.example.movieapp.navigation

import android.app.FragmentManager.BackStackEntry
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.screens.DetailScreen
import com.example.movieapp.screens.FavouriteScreen
import com.example.movieapp.screens.HomeScreen

@Composable
fun MyNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "homescreen") {
        composable("favourites") { FavouriteScreen(navController = navController) }
        composable("homescreen") { HomeScreen(navController = navController) }
        composable("detailscreen/{id}",
            arguments = listOf( navArgument("id") { type = NavType.StringType })
        ) {
            DetailScreen(
                navController = navController,
                id = it.arguments?.getString("id").toString()
            )
        }
    }
}