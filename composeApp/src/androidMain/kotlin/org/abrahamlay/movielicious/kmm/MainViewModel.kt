package org.abrahamlay.movielicious.kmm

import android.util.Log
import androidx.lifecycle.ViewModel
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataResult
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetNowPlayingCollection
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetPopularCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPopularCollection: GetPopularCollection,
    private val getNowPlayingCollection: GetNowPlayingCollection
) :
    ViewModel() {

    private val _resultPopular = MutableStateFlow<DataResult<List<Movie>>>(
        DataResult.Success(
            emptyList()
        )
    )
    val resultPopular: StateFlow<DataResult<List<Movie>>> = _resultPopular
    fun fetchPopular() {
        getPopularCollection {
            when (it) {
                is DataResult.Loading -> _resultPopular.value = it
                is DataResult.Success -> {
                    Log.d("TAG", "getPopularCollection success: ${it.result}")
                    _resultPopular.value = it
                }

                is DataResult.Failure -> {
                    Log.d("TAG", "getPopularCollection failed: ${it.message}")
                    _resultPopular.value = it
                }
            }
        }
    }

    private val _resultNowPlaying = MutableStateFlow<DataResult<List<Movie>>>(
        DataResult.Success(
            emptyList()
        )
    )
    val resultNowPlaying: StateFlow<DataResult<List<Movie>>> = _resultNowPlaying

    fun fetchNowPlaying() {
        getNowPlayingCollection {
            when (it) {
                is DataResult.Loading -> _resultNowPlaying.value = it
                is DataResult.Success -> {
                    Log.d("TAG", "getNowPlayingCollection success: ${it.result}")
                    _resultNowPlaying.value = it
                }

                is DataResult.Failure -> {
                    Log.d("TAG", "getNowPlayingCollection failed: ${it.message}")
                    _resultNowPlaying.value = it
                }
            }
        }
    }
}