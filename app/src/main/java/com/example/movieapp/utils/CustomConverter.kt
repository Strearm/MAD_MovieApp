package com.example.movieapp.utils

import androidx.room.TypeConverter
import com.example.movieapp.models.Genre

class CustomConverter {

    @TypeConverter
    fun listToString(list: List<String>) = list.joinToString { "," }

    @TypeConverter
    fun stringToList(string: String) = string.split(",").map { it.trim() }

}