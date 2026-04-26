package com.abrahamlay.movielicious.kmm.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.abrahamlay.movielicious.kmm.movie.constants.Constants
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import java.math.RoundingMode

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieCardHorizontal(
    movieModel: Movie,
    onMovieClicked: (Movie) -> Unit = {}
) {
    Column(Modifier.padding(8.dp)) {
        Card(
            elevation = 6.dp,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(8.dp)
                .heightIn(240.dp, 300.dp)
                .clickable { onMovieClicked(movieModel) }
        ) {
            PosterImage(movieModel)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Column {
            Text(
                text = movieModel.title ?: "Unknown Title",
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 4.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "rating",
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                    style = MaterialTheme.typography.subtitle1,
                    text = "${ratingRounding(movieModel)}/10 IMDb",
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieCardVertical(
    movieModel: Movie,
    onMovieClicked: (Movie) -> Unit = {}
) {
    Row(Modifier.padding(8.dp)) {
        Card(
            elevation = 6.dp,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(8.dp)
                .heightIn(160.dp, 180.dp)
                .widthIn(80.dp, 120.dp)
                .clickable { onMovieClicked(movieModel) }
        ) {
            PosterImage(movieModel)
        }
        Column {
            Text(
                text = movieModel.title ?: "Unknown Title",
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 4.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "rating",
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                    style = MaterialTheme.typography.caption,
                    text = "${ratingRounding(movieModel)}/10 IMDb",
                )
            }
        }
    }
}

private fun ratingRounding(movieModel: Movie) =
    movieModel.voteAverage.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()

@Composable
private fun PosterImage(item: Movie) {
    val image = item.posterPath
    val url =
        if (image?.contains("https://")?.not() == true || image?.contains("http://")
            ?.not() == true
        ) String.format(
            Constants.MOVIE_THUMBNAIL_BASE_URL_MEDIUM,
            image
        ) else image
    CoilImage(
        imageModel = { url },
        imageOptions = ImageOptions(
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        ),
        modifier = Modifier.fillMaxSize(),
    )
}