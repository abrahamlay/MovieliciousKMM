package org.abrahamlay.movielicious.kmm.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataResult
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import org.abrahamlay.movielicious.kmm.home.HomeContract
import org.abrahamlay.movielicious.kmm.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Collections


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeComponent(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToDetail: (Int) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeContract.Effect.NavigateToDetail -> onNavigateToDetail(effect.movieId)
                is HomeContract.Effect.ShowError -> {}
            }
        }
    }

    BackdropScaffold(
        modifier = Modifier.fillMaxSize(),
        frontLayerScrimColor = Color.Transparent,
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
        appBar = {
            Toolbar(Modifier) { showMenu() }
        },
        frontLayerContent = {
            Section(
                title = "Popular Movies",
                moviesResult = state.popularMovies,
                onMovieClicked = { movie ->
                    viewModel.setIntent(HomeContract.Intent.MovieClicked(movie))
                }
            )
        },
        backLayerContent = {
            Section(
                title = "Now Playing",
                moviesResult = state.nowPlayingMovies,
                onMovieClicked = { movie ->
                    viewModel.setIntent(HomeContract.Intent.MovieClicked(movie))
                }
            )
        },
        snackbarHost = {
            SnackbarHost(it)
        }
    )
}

private fun showMenu() {}

@Composable
private fun Section(
    title: String,
    moviesResult: DataResult<List<Movie>>,
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
        when (moviesResult) {
            is DataResult.Success -> {
                ItemSection(
                    movies = moviesResult.result,
                    onMovieClicked = onMovieClicked
                )
            }
            is DataResult.Loading -> {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Loading..."
                )
            }
            is DataResult.Failure -> {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Error: ${moviesResult.message}",
                    color = MaterialTheme.colors.error
                )
            }
        }
    }
}

@Composable
private fun ItemSection(
    movies: List<Movie>,
    onMovieClicked: (Movie) -> Unit
) {
    LazyRow(
        Modifier.height(200.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        val movieList = movies.ifEmpty { Collections.emptyList() }
        items(movieList) { movie ->
            CardHorizontal(
                movie = movie,
                onMovieClicked = onMovieClicked
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ArcList() {
    LazyRow(
        Modifier.height(200.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(20) {
            Column {
                Text("Item $it", modifier = Modifier.padding(16.dp))
            }
        }
    }
}