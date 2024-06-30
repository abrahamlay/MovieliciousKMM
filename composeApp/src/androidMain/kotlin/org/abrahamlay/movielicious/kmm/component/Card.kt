package org.abrahamlay.movielicious.kmm.component

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.abrahamlay.movielicious.kmm.movie.constants.Constants
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.abrahamlay.movielicious.kmm.R
import java.math.RoundingMode

@ExperimentalCoroutinesApi
@Composable
fun CardHorizontal(movieModel: Movie) {
    val context = LocalContext.current
    Column(Modifier.padding(8.dp)) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(8.dp)
                .heightIn(240.dp, 300.dp)
                .clickable {
                    Toast
                        .makeText(
                            context, "${movieModel.title}",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }) {
            PosterImage(movieModel)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Column() {
            Text(
                text = movieModel.title ?: "Unknown Title",
                style = MaterialTheme.typography.subtitle2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 4.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = "rating"
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

@Composable
fun CardVertical(movieModel: Movie) {
    val context = LocalContext.current
    Row(Modifier.padding(8.dp)) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(8.dp)
                .heightIn(160.dp, 180.dp)
                .widthIn(80.dp, 120.dp)
                .clickable {
                    Toast
                        .makeText(
                            context, "${movieModel.title}",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }) {
            PosterImage(movieModel)
        }
        Column() {
            Text(
                text = movieModel.title ?: "Unknown Title",
                style = MaterialTheme.typography.subtitle2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 4.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = "rating"
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                    style = MaterialTheme.typography.caption,
                    text = "${ratingRounding(movieModel)}/10 IMDb",
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                AssistChip(
                    onClick = { Log.d("Assist chip", "hello world") },
                    label = {
                        Text(
                            "Horror",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.caption
                        )
                    })
                AssistChip(
                    onClick = { Log.d("Assist chip", "hello world") },
                    label = {
                        Text(
                            "Horror",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.caption
                        )
                    })
                AssistChip(
                    onClick = { Log.d("Assist chip", "hello world") },
                    label = {
                        Text(
                            "Horror",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.caption
                        )
                    })
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Preview
@Composable
fun CardHorizontalPreview() {
    val movieDummy = Movie(
        voteCount = 1073,
        id = 1022789,
        video = false,
        voteAverage = 7.77,
        title = "Inside Out 2",
        popularity = 5541.227,
        posterPath = "/vpnVM9B6NMmQpWeZvzLvDESb2QY.jpg",
        originalLanguage = "en",
        originalTitle = "Inside Out 2",
        genreIds = listOf(16, 10751, 12, 35),
        backdropPath = "/xg27NrXi7VXCGUr7MG75UqLl6Vg.jpg",
        adult = false,
        overview = "Teenager Riley's mind headquarters is undergoing a sudden demolition to make room for something entirely unexpected: new Emotions! Joy, Sadness, Anger, Fear and Disgust, who’ve long been running a successful operation by all accounts, aren’t sure how to feel when Anxiety shows up. And it looks like she’s not alone.",
        releaseDate = "2024-06-11"
    )
    CardHorizontal(movieDummy)
}

@OptIn(ExperimentalCoroutinesApi::class)
@Preview
@Composable
fun CardVerticalPreview() {
    val movieDummy = Movie(
        voteCount = 1073,
        id = 1022789,
        video = false,
        voteAverage = 7.77,
        title = "Inside Out 2",
        popularity = 5541.227,
        posterPath = "/vpnVM9B6NMmQpWeZvzLvDESb2QY.jpg",
        originalLanguage = "en",
        originalTitle = "Inside Out 2",
        genreIds = listOf(16, 10751, 12, 35),
        backdropPath = "/xg27NrXi7VXCGUr7MG75UqLl6Vg.jpg",
        adult = false,
        overview = "Teenager Riley's mind headquarters is undergoing a sudden demolition to make room for something entirely unexpected: new Emotions! Joy, Sadness, Anger, Fear and Disgust, who’ve long been running a successful operation by all accounts, aren’t sure how to feel when Anxiety shows up. And it looks like she’s not alone.",
        releaseDate = "2024-06-11"
    )
    CardVertical(movieDummy)
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
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),

        )
}