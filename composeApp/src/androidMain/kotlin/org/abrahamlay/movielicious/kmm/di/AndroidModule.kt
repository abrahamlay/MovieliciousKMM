package org.abrahamlay.movielicious.kmm.di

import com.abrahamlay.movielicious.kmm.movie.data.api.NowPlayingMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.api.PopularMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.api.TopRatedMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.datasource.MovieRemoteDataSourceImpl
import com.abrahamlay.movielicious.kmm.movie.data.repository.MovieRepositoryImpl
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.datasource.MovieRemoteDataSource
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.repository.MovieRepository
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetPopularCollection
import org.abrahamlay.movielicious.kmm.home.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.get

val androidModule = module {
    single { PopularMovieApi() }
    single { TopRatedMovieApi() }
    single { NowPlayingMovieApi() }
    single {
        MovieRemoteDataSourceImpl(
            get(PopularMovieApi::class.java),
            get(TopRatedMovieApi::class.java),
            get(NowPlayingMovieApi::class.java)
        ) as MovieRemoteDataSource
    }
    single { MovieRepositoryImpl(get(MovieRemoteDataSource::class.java)) as MovieRepository}
    single { GetPopularCollection(get()) }
    viewModel { MainViewModel(get()) }
}