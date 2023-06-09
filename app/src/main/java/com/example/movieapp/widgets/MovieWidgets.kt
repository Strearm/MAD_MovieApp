package com.example.movieapp.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.movieapp.MovieViewModel
import com.example.movieapp.R
import com.example.movieapp.models.Movie
import com.example.movieapp.models.getMovies
import com.example.movieapp.ui.theme.Shapes
import kotlinx.coroutines.launch


@Composable
fun MovieRow(
    movie: Movie = getMovies()[0],
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit = {},
    onFavClick: (Movie) -> Unit = {},
    onDelClick: (Movie) -> Unit = {},
) {
    Card(modifier = modifier
        .clickable {
            onItemClick(movie.id.toString())
        }
        .fillMaxWidth()
        .padding(5.dp),
        shape = Shapes.large,
        elevation = 10.dp
    ) {
        Column {
            Box(modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                MovieImage(imageUrl = movie.images[0])
                FavoriteIcon(onFavClick, movie = movie)
            }

            MovieDetails(modifier = Modifier.padding(12.dp), movie = movie, onDelClick)
        }
    }
}

@Composable
fun MovieImage(imageUrl: String) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(id = R.string.movie_poster),
        loading = {
            CircularProgressIndicator()
        }
    )


}

@Composable
fun FavoriteIcon(onFavClick: (Movie) -> Unit,movie: Movie) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        contentAlignment = Alignment.TopEnd
    ){
        Icon(tint = MaterialTheme.colors.secondary,
            imageVector = if(movie.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Add to favorites",
            modifier = Modifier.clickable { onFavClick(movie)
            }
        )
    }
}


@Composable
fun MovieDetails(modifier: Modifier = Modifier, movie: Movie, onDelClick: (Movie) -> Unit) {

    var expanded by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            movie.title,
            modifier = Modifier.weight(6f),
            style = MaterialTheme.typography.h6
        )
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = {
                coroutineScope.launch {
                    onDelClick(movie)
                }
            }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Button")
        }

        IconButton(
            modifier = Modifier.weight(1f),
            onClick = { expanded = !expanded }) {
            Icon(imageVector =
            if (expanded) Icons.Filled.KeyboardArrowDown
            else Icons.Filled.KeyboardArrowUp,
                contentDescription = "expand",
                modifier = Modifier
                    .size(25.dp),
                tint = Color.DarkGray
            )
        }
    }

    AnimatedVisibility(
        visible = expanded,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column (modifier = modifier) {
            Text(text = "Director: ${movie.director}", style = MaterialTheme.typography.caption)
            Text(text = "Released: ${movie.year}", style = MaterialTheme.typography.caption)
            Text(text = "Genre: ${movie.genre}", style = MaterialTheme.typography.caption)
            Text(text = "Actors: ${movie.actors}", style = MaterialTheme.typography.caption)
            Text(text = "Rating: ${movie.rating}", style = MaterialTheme.typography.caption)

            Divider(modifier = Modifier.padding(3.dp))

            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.DarkGray, fontSize = 13.sp)) {
                    append("Plot: ")
                }
                withStyle(style = SpanStyle(color = Color.DarkGray, fontSize = 13.sp, fontWeight = FontWeight.Light)){
                    append(movie.plot)
                }
            })
        }
    }
}

@Composable
fun HorizontalScrollableImageView(movie: Movie) {
    LazyRow {
        items(movie.images) { image ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(240.dp),
                elevation = 4.dp
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Movie poster",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}