package com.example.movieapp.screens

import android.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapp.MovieViewModel
import com.example.movieapp.R
import com.example.movieapp.models.Genre
import com.example.movieapp.models.ListItemSelectable
import com.example.movieapp.models.Movie
import com.example.movieapp.widgets.SimpleTopAppBar

@Composable
fun AddMovieScreen(navController: NavController, movieViewModel: MovieViewModel){
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                Text(text = stringResource(id = R.string.add_movie))
            }
        },
    ) { padding ->
        MainContent(Modifier.padding(padding), movieViewModel = movieViewModel, navController = navController)
    }
}

@Composable
fun ErrorMessage(extra: String = ""){
    Text(text = "Cannot be empty. $extra", color = MaterialTheme.colors.error)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(modifier: Modifier = Modifier, movieViewModel: MovieViewModel, navController: NavController) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {

            var title by rememberSaveable {
                mutableStateOf("")
            }

            var year by rememberSaveable {
                mutableStateOf("")
            }

            val genres = Genre.values().toList()

            var genreItems by rememberSaveable {
                mutableStateOf(
                    genres.map { genre ->
                        ListItemSelectable(
                            title = genre.toString(),
                            isSelected = false
                        )
                    }
                )
            }

            var director by rememberSaveable {
                mutableStateOf("")
            }

            var actors by rememberSaveable {
                mutableStateOf("")
            }

            var plot by rememberSaveable {
                mutableStateOf("")
            }

            var rating by rememberSaveable {
                mutableStateOf("")
            }

            var isEnabledSaveButton by rememberSaveable {
                mutableStateOf(false)
            }

            var titleError by rememberSaveable { mutableStateOf(true) }
            var yearError by rememberSaveable { mutableStateOf(true) }
            var genreError by rememberSaveable { mutableStateOf(true) }
            var directorError by rememberSaveable { mutableStateOf(true) }
            var actorsError by rememberSaveable { mutableStateOf(true) }
            var ratingError by rememberSaveable{mutableStateOf(true) }


            Column {
                OutlinedTextField(
                    value = title,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { title = it
                        titleError = movieViewModel.validateInputNotEmpty(title)
                        //isEnabledSaveButton = buttonCheck(titleError,yearError,directorError,actorsError)
                        },
                    label = { Text(text = stringResource(R.string.enter_movie_title)) },
                    isError = titleError,
                )
                if(titleError){
                    ErrorMessage()
                }
            }

            Column {
                OutlinedTextField(
                    value = year,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { year = it
                        yearError = movieViewModel.validateInputNotEmpty(year)
                        //isEnabledSaveButton = buttonCheck(titleError,yearError,directorError,actorsError)
                    },
                    label = { Text(stringResource(R.string.enter_movie_year)) },
                    isError = yearError
                )
                if(yearError){
                    ErrorMessage()
                }
            }

            Column {
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = stringResource(R.string.select_genres),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h6)

                LazyHorizontalGrid(
                    modifier = Modifier.height(100.dp),
                    rows = GridCells.Fixed(3)){
                    items(genreItems) { genreItem ->
                        Chip(
                            modifier = Modifier.padding(2.dp),
                            colors = ChipDefaults.chipColors(
                                backgroundColor = if (genreItem.isSelected)
                                    colorResource(id = R.color.purple_200)
                                else
                                    colorResource(id = R.color.white)
                            ),
                            onClick = {
                                genreItems = genreItems.map {
                                    if (it.title == genreItem.title) {
                                        genreItem.copy(isSelected = !genreItem.isSelected)
                                    } else {
                                        it
                                    }
                                }
                                genreError = !genreItems.any { it.isSelected }
                            }
                        ) {
                            Text(text = genreItem.title)
                        }
                    }
                }
                if (genreError){
                    ErrorMessage("Select at least one Genre")
                }
            }

            Column {
                OutlinedTextField(
                    value = director,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { director = it
                        directorError = movieViewModel.validateInputNotEmpty(director)
                        //isEnabledSaveButton = buttonCheck(titleError,yearError,directorError,actorsError)
                        },
                    label = { Text(stringResource(R.string.enter_director)) },
                    isError = directorError
                )
                if(directorError){
                    ErrorMessage()
                }
            }

            Column {
                OutlinedTextField(
                    value = actors,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { actors = it
                        actorsError = movieViewModel.validateInputNotEmpty(actors)
                        //isEnabledSaveButton = buttonCheck(titleError,yearError,directorError,actorsError)
                        },
                    label = { Text(stringResource(R.string.enter_actors)) },
                    isError = actorsError
                )
                if(actorsError){
                    ErrorMessage()
                }
            }

            OutlinedTextField(
                value = plot,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                onValueChange = { plot = it },
                label = { Text(textAlign = TextAlign.Start, text = stringResource(R.string.enter_plot)) },
                isError = false
            )

            Column() {
                OutlinedTextField(
                    value = rating,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        rating = if(it.startsWith("0")) {
                            ""
                        } else {
                            it
                        }
                        ratingError = movieViewModel.validateInputFloat(rating)
                    },
                    label = { Text(stringResource(R.string.enter_rating)) },
                    isError = ratingError
                )
                if(ratingError){
                    ErrorMessage(extra = "Must be a Number")
                }
            }

            isEnabledSaveButton = !titleError && !yearError && !directorError && !actorsError && !ratingError && !genreError

            val genreList: ArrayList<Genre> = ArrayList()

            for (i in genres.indices) {
                if(genreItems[i].isSelected){
                    genreList.add(genres[i])
                }
            }

            Button(
                enabled = isEnabledSaveButton,
                onClick = { movieViewModel.addMovie(Movie(id = "${movieViewModel.movieList.size + 1}",
                    title = title,
                    year = year,
                    genre = genreList,
                    director = director,
                    actors = actors,
                    plot = plot,
                    images = listOf("https://t3.ftcdn.net/jpg/02/48/42/64/360_F_248426448_NVKLywWqArG2ADUxDq6QprtIzsF82dMF.jpg"),
                    rating = rating.toFloat(),
                    favourite = false))

                    navController.navigate(route = Screen.MainScreen.route)
                }
            ) {
                Text(text = stringResource(R.string.add))
            }
        }
    }
}