package org.abrahamlay.movielicious.kmm.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataResult
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetNowPlayingCollection
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetPopularCollection
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetTopRatedCollection
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface HomeContract {
    @Immutable
    data class State(
        val popularMovies: DataResult<List<Movie>> = DataResult.Loading,
        val nowPlayingMovies: DataResult<List<Movie>> = DataResult.Loading,
        val topRatedMovies: DataResult<List<Movie>> = DataResult.Loading
    )

    sealed interface Intent {
        data object FetchPopularMovies : Intent
        data object FetchNowPlayingMovies : Intent
        data object FetchTopRatedMovies : Intent
        data class MovieClicked(val movie: Movie) : Intent
    }

    sealed interface Effect {
        data class NavigateToDetail(val movieId: Int) : Effect
        data class ShowError(val message: String) : Effect
    }
}

class HomeViewModel(
    private val getPopularMovies: GetPopularCollection,
    private val getNowPlayingMovies: GetNowPlayingCollection,
    private val getTopRatedMovies: GetTopRatedCollection
) : ViewModel() {

    private val _state = MutableStateFlow(HomeContract.State())
    val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    private val _effect = Channel<HomeContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        setIntent(HomeContract.Intent.FetchPopularMovies)
        setIntent(HomeContract.Intent.FetchNowPlayingMovies)
        setIntent(HomeContract.Intent.FetchTopRatedMovies)
    }

    fun setIntent(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.FetchPopularMovies -> fetchPopularMovies()
            is HomeContract.Intent.FetchNowPlayingMovies -> fetchNowPlayingMovies()
            is HomeContract.Intent.FetchTopRatedMovies -> fetchTopRatedMovies()
            is HomeContract.Intent.MovieClicked -> handleMovieClicked(intent.movie)
        }
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            _state.update { it.copy(popularMovies = DataResult.Loading) }
            getPopularMovies.invoke { result ->
                _state.update {
                    it.copy(
                        popularMovies = when (result.status) {
                            com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataStatus.SUCCESS ->
                                DataResult.Success(result.data ?: emptyList())
                            com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataStatus.ERROR ->
                                DataResult.Failure(result.errorCode, result.errorMessage)
                            com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataStatus.LOADING ->
                                DataResult.Loading
                        }
                    )
                }
            }
        }
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            _state.update { it.copy(nowPlayingMovies = DataResult.Loading) }
            getNowPlayingMovies.invoke { result ->
                _state.update {
                    it.copy(
                        nowPlayingMovies = when (result.status) {
                            com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataStatus.SUCCESS ->
                                DataResult.Success(result.data ?: emptyList())
                            com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataStatus.ERROR ->
                                DataResult.Failure(result.errorCode, result.errorMessage)
                            com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataStatus.LOADING ->
                                DataResult.Loading
                        }
                    )
                }
            }
        }
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch {
            _state.update { it.copy(topRatedMovies = DataResult.Loading) }
            getTopRatedMovies.invoke { result ->
                _state.update {
                    it.copy(
                        topRatedMovies = when (result.status) {
                            com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataStatus.SUCCESS ->
                                DataResult.Success(result.data ?: emptyList())
                            com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataStatus.ERROR ->
                                DataResult.Failure(result.errorCode, result.errorMessage)
                            com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataStatus.LOADING ->
                                DataResult.Loading
                        }
                    )
                }
            }
        }
    }

    private fun handleMovieClicked(movie: Movie) {
        viewModelScope.launch {
            _effect.send(HomeContract.Effect.NavigateToDetail(movie.id))
        }
    }

    override fun onCleared() {
        super.onCleared()
        getPopularMovies.dispose()
        getNowPlayingMovies.dispose()
    }
}