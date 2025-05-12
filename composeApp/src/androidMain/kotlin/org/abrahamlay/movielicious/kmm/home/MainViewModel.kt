package org.abrahamlay.movielicious.kmm.home

import androidx.lifecycle.ViewModel
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataResult
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetPopularCollection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel constructor(
    private val getPopularMovies: GetPopularCollection
) : ViewModel() {

    val _movies =
        MutableStateFlow<DataResult<List<Movie>>>(DataResult.Loading)
    val movies: StateFlow<DataResult<List<Movie>>> =
        _movies

    fun fetchMovie() {
        getPopularMovies.invoke { result ->
            _movies.update {  result }
        }
    }

    override fun onCleared() {
        super.onCleared()
        getPopularMovies.dispose()
    }
}