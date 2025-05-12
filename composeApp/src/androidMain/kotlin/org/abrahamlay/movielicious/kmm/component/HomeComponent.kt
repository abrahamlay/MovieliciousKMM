package org.abrahamlay.movielicious.kmm.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataResult
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.abrahamlay.movielicious.kmm.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Collections


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeComponent() {
    BackdropScaffold(
        modifier = Modifier.fillMaxSize(),
        frontLayerScrimColor = Color.Transparent,
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
        appBar = {
            Toolbar(Modifier) { showMenu() }
        },
        frontLayerContent = {
            Section(uiAction = HomeViewModel.UiAction.POPULAR)
        },
        backLayerContent = {
            Section(uiAction = HomeViewModel.UiAction.NOW_PLAYING)
        },
        snackbarHost = {
            SnackbarHost(it)
        }
    )
}

private fun showMenu() {

}

@Composable
private fun Section(
    viewModel: HomeViewModel = koinViewModel(),
    uiAction: HomeViewModel.UiAction = HomeViewModel.UiAction.POPULAR
) {
    LaunchedEffect(viewModel) {
        when (uiAction) {
            HomeViewModel.UiAction.POPULAR -> viewModel.fetchPopularMovies()
            HomeViewModel.UiAction.NOW_PLAYING -> viewModel.fetchNowPlayingMovies()
            HomeViewModel.UiAction.TOP_RATED -> viewModel.fetchTopRatedMovies()
        }

    }
    val result by when (uiAction) {
        HomeViewModel.UiAction.POPULAR -> viewModel.popularMovies.collectAsState()
        HomeViewModel.UiAction.NOW_PLAYING -> viewModel.nowPlayingMovies.collectAsState()
        HomeViewModel.UiAction.TOP_RATED -> viewModel.topRatedMovies.collectAsState()
    }

    when (result) {
        is DataResult.Success -> {
            ItemSection((result as DataResult.Success<List<Movie>>).result)
        }

        else -> Unit
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun ItemSection(result: List<Movie>?) {
    Column(
        Modifier.padding(start = 0.dp, top = 8.dp, end = 0.dp, bottom = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Popular Movies",
            style = MaterialTheme.typography.h5
        )
        LazyRow() {
            val movies = result ?: Collections.emptyList()
            items(movies) { movie ->
                CardHorizontal(movie)
            }
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