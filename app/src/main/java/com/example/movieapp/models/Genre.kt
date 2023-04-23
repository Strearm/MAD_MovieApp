package com.example.movieapp.models

enum class Genre{
    ACTION,
    ADVENTURE,
    BIOGRAPHY,
    COMEDY,
    CRIME,
    FANTASY,
    DRAMA,
    HISTORY,
    SCIFI,
    THRILLER;

    val value: Int
        get() = ordinal + 1


    companion object {

        private val ENUMS = Genre.values()

        fun of(genre: Int): Genre {
            if (genre < 1 || genre > 7) {
                throw RuntimeException("Invalid value for genre: " + genre)
            }

            return ENUMS[genre - 1]
        }

    }
}