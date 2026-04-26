package org.abrahamlay.movielicious.kmm.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataResult
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import com.abrahamlay.movielicious.kmm.ui.screens.home.HomeSection
import com.abrahamlay.movielicious.kmm.ui.theme.MovieliciousTheme
import org.abrahamlay.movielicious.kmm.home.HomeContract
import org.koin.androidx.compose.koinViewModel
import org.abrahamlay.movielicious.kmm.home.HomeViewModel

@Composable
fun App() {
    MovieliciousTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            HomeComponentWithViewModel()
        }
    }
}

@Composable
private fun HomeComponentWithViewModel(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeContract.Effect.NavigateToDetail -> {}
                is HomeContract.Effect.ShowError -> {}
            }
        }
    }

    val popularMovies = when (val result = state.popularMovies) {
        is DataResult.Success -> result.result
        else -> emptyList()
    }
    val popularLoading = state.popularMovies is DataResult.Loading

    val nowPlayingMovies = when (val result = state.nowPlayingMovies) {
        is DataResult.Success -> result.result
        else -> emptyList()
    }
    val nowPlayingLoading = state.nowPlayingMovies is DataResult.Loading

    BackdropScaffoldContent(
        popularMovies = popularMovies,
        popularLoading = popularLoading,
        nowPlayingMovies = nowPlayingMovies,
        nowPlayingLoading = nowPlayingLoading,
        onMovieClicked = { movie ->
            viewModel.setIntent(HomeContract.Intent.MovieClicked(movie))
        }
    )
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun BackdropScaffoldContent(
    popularMovies: List<Movie>,
    popularLoading: Boolean,
    nowPlayingMovies: List<Movie>,
    nowPlayingLoading: Boolean,
    onMovieClicked: (Movie) -> Unit
) {
    androidx.compose.material.BackdropScaffold(
        modifier = Modifier.fillMaxSize(),
        frontLayerScrimColor = androidx.compose.ui.graphics.Color.Transparent,
        scaffoldState = androidx.compose.material.rememberBackdropScaffoldState(androidx.compose.material.BackdropValue.Revealed),
        appBar = {
            Toolbar(Modifier) { }
        },
        frontLayerContent = {
            HomeSection(
                title = "Popular Movies",
                movies = popularMovies,
                isLoading = popularLoading,
                onMovieClicked = onMovieClicked
            )
        },
        backLayerContent = {
            HomeSection(
                title = "Now Playing",
                movies = nowPlayingMovies,
                isLoading = nowPlayingLoading,
                onMovieClicked = onMovieClicked
            )
        },
        snackbarHost = {
            androidx.compose.material.SnackbarHost(it)
        }
    )
}
