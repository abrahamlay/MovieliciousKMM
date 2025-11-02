package org.abrahamlay.movielicious.kmm.component

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataResult
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.abrahamlay.movielicious.kmm.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Collections
import kotlin.math.absoluteValue


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeComponent() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Section(
            uiAction = HomeViewModel.UiAction.POPULAR,
            sectionType = HomeViewModel.SectionType.SHOWCASE
        )
    }
//    BackdropScaffold(
//        modifier = Modifier.fillMaxSize(),
//        frontLayerScrimColor = Color.Transparent,
//        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
//        appBar = {
//            Toolbar(Modifier) { showMenu() }
//        },
//        frontLayerContent = {
//            Section(
//                uiAction = HomeViewModel.UiAction.POPULAR,
//                sectionType = HomeViewModel.SectionType.SHOWCASE
//            )
//        },
//        backLayerContent = {
//            Section(uiAction = HomeViewModel.UiAction.NOW_PLAYING)
//        },
//        snackbarHost = {
//            SnackbarHost(it)
//        }
//    )
}

private fun showMenu() {

}

@Composable
private fun Section(
    viewModel: HomeViewModel = koinViewModel(),
    uiAction: HomeViewModel.UiAction = HomeViewModel.UiAction.POPULAR,
    sectionType: HomeViewModel.SectionType = HomeViewModel.SectionType.HORIZONTAL_LIST
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
            when (sectionType) {
                HomeViewModel.SectionType.HORIZONTAL_LIST -> ItemSection((result as DataResult.Success<List<Movie>>).result)
                HomeViewModel.SectionType.SHOWCASE -> ShowcaseSection((result as DataResult.Success<List<Movie>>).result)
            }
        }

        else -> {
            Unit
        }
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


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun ShowcaseSection(result: List<Movie>?) {
    val context = LocalContext.current
    Box {

        val pagerState = rememberPagerState(
            initialPage = 4,
            initialPageOffsetFraction = 0.3f,
            pageCount = { result?.size ?: 0 })
        val totalScrollProgress = pagerState.currentPage + pagerState.currentPageOffsetFraction

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(800.dp, 900.dp)
                .graphicsLayer {
//                    val scrollAmount = totalScrollProgress * size.width
//                    translationX = -scrollAmount
                },
        ) {
            val verticalGradient = Brush.verticalGradient(
                startY = 2f,
                colors = listOf(Color.Transparent, Color.White)
            )
            BackdropImage(result?.get(pagerState.currentPage))
            Box(
                Modifier
                    .fillMaxSize()
                    .background(brush = verticalGradient)
            )
        }
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 64.dp),
            pageSpacing = (-8).dp,
            snapPosition = SnapPosition.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) { page ->
            val pageOffset =
                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

            val scale = lerp(
                start = 0.75f, // Mengecil hingga 75%
                stop = 1f,     // Ukuran penuh
                fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
            )

            val translation = lerp(
                start = 0.75f, // Mengecil hingga 75%
                stop = 1f,     // Ukuran penuh
                fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
            )

            val alpha = lerp(
                start = 0.5f, // Memudar hingga 50%
                stop = 1f,
                fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
            )
            Box(contentAlignment = Alignment.BottomCenter) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .graphicsLayer {
                            // Terapkan kalkulasi di sini
                            scaleX = scale
                            scaleY = scale
//                            this.alpha = alpha
                        }) {
                    CardShowcase(result?.get(page))
                }
            }
        }
        Button(
            onClick = {
                Toast.makeText(context, "BUY TICKET", Toast.LENGTH_SHORT).show()
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier
                .widthIn(240.dp, 280.dp)
                .heightIn(72.dp, 84.dp)
                .padding(start = 20.dp, bottom = 30.dp, end = 20.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text("BUY TICKET")
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

@Preview(showSystemUi = true)
@Composable
fun ShowcaseMoviePreview() {
    ShowcaseSection(
        listOf(
            Movie(
                id = 1,
                title = "Movie 1",
                overview = "Overview 1",
                posterPath = "/path1.jpg",
                backdropPath = "/backdrop1.jpg",
                releaseDate = "2023-01-01",
                voteAverage = 8.5,
                voteCount = 500,
                video = false,
                popularity = 150.0,
                originalLanguage = "en",
                originalTitle = "Original Movie 1",
                genreIds = listOf(1, 2, 3),
                adult = false
            ),
            Movie(
                id = 2,
                title = "Movie 2",
                overview = "Overview 2",
                posterPath = "/path2.jpg",
                backdropPath = "/backdrop2.jpg",
                releaseDate = "2023-02-01",
                voteAverage = 7.5,
                voteCount = 500,
                video = false,
                popularity = 150.0,
                originalLanguage = "en",
                originalTitle = "Original Movie 2",
                genreIds = listOf(1, 2, 3),
                adult = false
            ),
            Movie(
                id = 3,
                title = "Movie 3",
                overview = "Overview 3",
                posterPath = "/path3.jpg",
                backdropPath = "/backdrop3.jpg",
                releaseDate = "2023-03-01",
                voteAverage = 9.0,
                voteCount = 500,
                video = false,
                popularity = 150.0,
                originalLanguage = "en",
                originalTitle = "Original Movie 3",
                genreIds = listOf(1, 2, 3),
                adult = false
            )
        )
    )
}