package com.example.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun DetailScreen( navController: NavController, movieName: String){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            MenuBar(movieName)
        }
    }
}

@Composable
private fun MenuBar(movieName: String){
    var expanded by remember{
        mutableStateOf(false)
    }
    TopAppBar(modifier = Modifier
        .fillMaxWidth(),
    ) {
        Text(text = movieName)
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