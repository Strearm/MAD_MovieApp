package com.example.movieapp

const val MOVIE_KEY = "id"

sealed class Screen(val route: String){
    object Home: Screen(route = "homescreen")
    object Detail: Screen(route = "detailscreen/{$MOVIE_KEY}"){
        fun passId(id: String):String{
            return "detailscreen/$id"
        }
    }
    object Favourite: Screen(route = "favourites")
}
