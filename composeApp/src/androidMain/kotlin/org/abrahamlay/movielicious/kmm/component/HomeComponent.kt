package org.abrahamlay.movielicious.kmm.component

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataResult
import com.abrahamlay.movielicious.kmm.movie.constants.Constants
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import org.abrahamlay.movielicious.kmm.R
import org.abrahamlay.movielicious.kmm.home.MainViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Collections


@Composable
fun HomeComponent() {
    Column(
        modifier = Modifier
            .padding(start = 0.dp, top = 16.dp, end = 0.dp, bottom = 0.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
    ) {
        Toolbar()
        LazyColumn {
            items(count = 1) {
                Section()
                Divider()
                Section()
            }
        }
    }
}

@Composable
private fun Section(viewModel: MainViewModel = koinViewModel()) {
    LaunchedEffect(viewModel) {
        viewModel.fetchMovie()
    }
    val result by viewModel.movies.collectAsState()

    when (result) {
        is DataResult.Success -> {
            ItemSection((result as DataResult.Success<List<Movie>>).result)
        }

        else -> Unit
    }
}

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
                ItemHorizontal(movie)
            }
        }
    }

}

@Composable
fun Toolbar() {
    val context = LocalContext.current
    TopAppBar(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        title = { Text(text = stringResource(id = R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.background,
        navigationIcon = {
            IconButton(
                onClick = {
                    Toast.makeText(
                        context,
                        "Menu Clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = "Menu"
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    Toast.makeText(
                        context,
                        "Search Clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Menu Search"
                )
            }
        },
        elevation = 0.dp
    )
}

@Composable
fun ItemHorizontal(movieModel: Movie?) {
    val context = LocalContext.current
    val url = movieModel?.posterPath
    var addedUrl = url
    if (!url.isNullOrEmpty()) {
        addedUrl =
            if (!url.contains("https://") || !url.contains("http://")) String.format(
                Constants.MOVIE_THUMBNAIL_BASE_URL_MEDIUM,
                url
            ) else url
    }
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier
                .clickable {
                    Toast.makeText(
                        context, "${movieModel?.title}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }) {
            CoilImage(
                modifier = Modifier
                    .aspectRatio(
                        ratio = 0.75F,
                        matchHeightConstraintsFirst = true
                    )
                    .heightIn(min = 280.dp, max = 360.dp),
                imageModel = { addedUrl }, // loading a network image or local resource using an URL.
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
            )
        }

        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(
            text = movieModel?.title ?: "Unknown Title",
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .width(180.dp)
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        )
        Row {
            Icon(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "rating"
            )
            Text(
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                text = "${movieModel?.voteAverage}"
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