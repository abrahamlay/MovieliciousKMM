package org.abrahamlay.movielicious.kmm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataResult
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.abrahamlay.movielicious.kmm.component.CardHorizontal
import org.abrahamlay.movielicious.kmm.component.CardVertical
import org.abrahamlay.movielicious.kmm.component.Toolbar

@AndroidEntryPoint(ComponentActivity::class)
class MainActivity : Hilt_MainActivity() {


    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val resultPopular = viewModel.resultPopular.collectAsState()
                val resultNowPlaying = viewModel.resultNowPlaying.collectAsState()
                LaunchedEffect(Unit) {
                    viewModel.fetchPopular()
                    viewModel.fetchNowPlaying()
                }
                BackdropScaffold(
                    modifier = Modifier.fillMaxSize(),
                    frontLayerScrimColor = Color.Transparent,
                    scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
                    appBar = {
                        Toolbar(Modifier) { showMenu() }
                    },
                    frontLayerContent = {
                        PopularSection(resultPopular)
                    },
                    backLayerContent = {
                        NowPlayingSection(resultNowPlaying)
                    },
                    snackbarHost = {
                        SnackbarHost(it)
                    }
                )
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Composable
    private fun NowPlayingSection(resultData: State<DataResult<List<Movie>>>) {
        Column(
            modifier = Modifier.padding(
                top = 12.dp,
                bottom = 12.dp
            )
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Now Playing",
                style = MaterialTheme.typography.h6
            )
            Spacer(Modifier.height(8.dp))
            when (val dataResult = resultData.value) {
                is DataResult.Loading -> {
                    Text(
                        modifier = Modifier.height(100.dp),
                        text = "Loading ...",
                        textAlign = TextAlign.Center
                    )
                }

                is DataResult.Success -> {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(dataResult.result) {
                            CardHorizontal(it)
                        }
                    }
                }

                is DataResult.Failure -> {
                    val error = dataResult
                    Snackbar {
                        Text(
                            text = error.message,
                            textAlign = TextAlign.Center
                        )
                    }

                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun PopularSection(resultData: State<DataResult<List<Movie>>>) {
        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                top = 12.dp,
                end = 16.dp
            )
        ) {
            Text(
                modifier = Modifier,
                text = "Popular",
                style = MaterialTheme.typography.h6
            )
            Spacer(Modifier.height(8.dp))
            when (val dataResult = resultData.value) {
                is DataResult.Loading -> {
                    Text(
                        modifier = Modifier.height(100.dp),
                        text = "Loading ...",
                        textAlign = TextAlign.Center
                    )
                }

                is DataResult.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(dataResult.result) {
                            CardVertical(it)
                        }
                    }
                }

                is DataResult.Failure -> {
                    val error = dataResult
                    Snackbar {
                        Text(
                            text = error.message,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    private fun showMenu() {

    }
}
//
//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App()
//}