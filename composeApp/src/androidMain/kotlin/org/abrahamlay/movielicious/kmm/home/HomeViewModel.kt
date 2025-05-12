package org.abrahamlay.movielicious.kmm.home

import androidx.lifecycle.ViewModel
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataResult
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataStatus
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetNowPlayingCollection
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetPopularCollection
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetTopRatedCollection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

open class HomeViewModel constructor(
    private val getPopularMovies: GetPopularCollection,
    private val getNowPlayingMovies: GetNowPlayingCollection,
    private val getTopRatedMovies: GetTopRatedCollection
) : ViewModel() {

    private val _popularMovies =
        MutableStateFlow<DataResult<List<Movie>>>(DataResult.Loading)
    val popularMovies: StateFlow<DataResult<List<Movie>>> =
        _popularMovies

    fun fetchPopularMovies() {
        getPopularMovies.invoke { result ->
            _popularMovies.update {
                when (result.status) {
                    DataStatus.SUCCESS -> DataResult.Success(result.data ?: emptyList())
                    DataStatus.ERROR -> DataResult.Failure(
                        errorCode = result.errorCode,
                        message = result.errorMessage
                    )
                    DataStatus.LOADING -> DataResult.Loading
                }
            }
        }
    }

    private val _nowPlayingMovies =
        MutableStateFlow<DataResult<List<Movie>>>(DataResult.Loading)
    val nowPlayingMovies: StateFlow<DataResult<List<Movie>>> =
        _nowPlayingMovies

    fun fetchNowPlayingMovies() {
        getNowPlayingMovies.invoke { result ->
            _nowPlayingMovies.update {
                when (result.status) {
                    DataStatus.SUCCESS -> DataResult.Success(result.data ?: emptyList())
                    DataStatus.ERROR -> DataResult.Failure(
                        errorCode = result.errorCode,
                        message = result.errorMessage
                    )
                    DataStatus.LOADING -> DataResult.Loading
                }
            }
        }
    }


    private val _topRatedMovies =
        MutableStateFlow<DataResult<List<Movie>>>(DataResult.Loading)
    val topRatedMovies: StateFlow<DataResult<List<Movie>>> =
        _topRatedMovies

    fun fetchTopRatedMovies() {
        getTopRatedMovies.invoke { result ->
            _topRatedMovies.update {
                when (result.status) {
                    DataStatus.SUCCESS -> DataResult.Success(result.data ?: emptyList())
                    DataStatus.ERROR -> DataResult.Failure(
                        errorCode = result.errorCode,
                        message = result.errorMessage
                    )
                    DataStatus.LOADING -> DataResult.Loading
                }
            }
        }
    }

    enum class UiAction {
        POPULAR,
        NOW_PLAYING,
        TOP_RATED
    }


    override fun onCleared() {
        super.onCleared()
        getPopularMovies.dispose()
        getNowPlayingMovies.dispose()
    }
}