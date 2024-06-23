package org.abrahamlay.movielicious.kmm

import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataResult
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetPopularCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getPopularCollection: GetPopularCollection) :
    ViewModel() {

    private val _result = MutableStateFlow<DataResult<List<Movie>>>(
        DataResult.Success(
            emptyList()
        )
    )
    val result: StateFlow<DataResult<List<Movie>>> = _result
    fun fetchPopular() {
        getPopularCollection {
            when (it) {
                is DataResult.Loading -> _result.value = it
                is DataResult.Success -> {
                    Log.d("TAG", "getPopularCollection success: ${it.result}")
                    _result.value = it
                }

                is DataResult.Failure -> {
                    Log.d("TAG", "getPopularCollection failed: ${it.message}")
                    _result.value = it
                }
            }
        }
    }
}