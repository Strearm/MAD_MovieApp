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
import com.example.movieapp.screens.HomeScreen

@Composable
fun MyNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "homescreen"){
        composable("homescreen") { HomeScreen(navController = navController) }
        composable(
            "detailscreen/{movieName}",
            arguments = listOf(navArgument("movieName"){
            type = NavType.StringType
        })
        ) {
            Log.d("args", it.arguments?.getString("movieName").toString())
            DetailScreen(navController = navController, movieName = it.arguments?.getString("movieName").toString()) }
    }
}