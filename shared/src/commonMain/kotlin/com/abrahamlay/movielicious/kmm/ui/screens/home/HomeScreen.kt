package com.abrahamlay.movielicious.kmm.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataResult
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import com.abrahamlay.movielicious.kmm.ui.components.MovieCardHorizontal
import com.abrahamlay.movielicious.kmm.ui.theme.MovieliciousTheme
import org.koin.compose.koinInject
import java.util.Collections

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    onNavigateToDetail: (Int) -> Unit = {}
) {
    MovieliciousTheme {
        BackdropScaffold(
            modifier = Modifier.fillMaxSize(),
            frontLayerScrimColor = Color.Transparent,
            scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
            appBar = {
                HomeToolbar()
            },
            frontLayerContent = {
                HomeSection(
                    title = "Popular Movies",
                    movies = emptyList(),
                    isLoading = true,
                    onMovieClicked = {}
                )
            },
            backLayerContent = {
                HomeSection(
                    title = "Now Playing",
                    movies = emptyList(),
                    isLoading = true,
                    onMovieClicked = {}
                )
            },
            snackbarHost = {
                SnackbarHost(it)
            }
        )
    }
}

@Composable
private fun HomeToolbar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Movielicious",
            style = MaterialTheme.typography.h5
        )
    }
}

@Composable
fun HomeSection(
    title: String,
    movies: List<Movie>,
    isLoading: Boolean,
    onMovieClicked: (Movie) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 0.dp, top = 8.dp, end = 0.dp, bottom = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = title,
            style = MaterialTheme.typography.h5
        )
        when {
            isLoading -> {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Loading..."
                )
            }
            movies.isEmpty() -> {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "No movies available"
                )
            }
            else -> {
                MovieListSection(movies = movies, onMovieClicked = onMovieClicked)
            }
        }
    }
}

@Composable
private fun MovieListSection(
    movies: List<Movie>,
    onMovieClicked: (Movie) -> Unit
) {
    LazyRow(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        val movieList = movies.ifEmpty { Collections.emptyList() }
        items(movieList) { movie ->
            MovieCardHorizontal(
                movieModel = movie,
                onMovieClicked = onMovieClicked
            )
        }
    }
}