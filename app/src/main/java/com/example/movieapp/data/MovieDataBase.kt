package com.example.movieapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.models.Movie
import com.example.movieapp.utils.CustomConverter

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = true
)

@TypeConverters(CustomConverter::class)
abstract class MovieDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile // never cache the value of Instance
        private var Instance: MovieDataBase? = null
        fun getDatabase(context: Context): MovieDataBase{
            return Instance ?: synchronized(this) { // wrap in synchronized block to prevent race conditions
                Room.databaseBuilder(context, MovieDataBase::class.java, "movie_db")
                    .fallbackToDestructiveMigration() // if schema changes wipe the whole schema - you could add your migration strategies here
                    .build() // create an instance of the db
                    .also {
                        Instance = it // override Instance with newly created db
                    }
            }
        }
    }
}