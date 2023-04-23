package com.example.movieapp.data

import androidx.room.*
import com.example.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert
    suspend fun add(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * from movie")
    fun getAll(): Flow<List<Movie>>

    @Query("SELECT * from movie WHERE isFavourite = 1")
    fun getFavourites(): Flow<List<Movie>>

    @Query("SELECT * from movie WHERE id =:id")
    suspend fun getMovieById(id: Int):Movie
}