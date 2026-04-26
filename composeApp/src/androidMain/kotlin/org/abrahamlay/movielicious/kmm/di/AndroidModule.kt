package org.abrahamlay.movielicious.kmm.di

import com.abrahamlay.movielicious.kmm.db.AppDatabase
import com.abrahamlay.movielicious.kmm.db.DriverFactory
import com.abrahamlay.movielicious.kmm.db.createDatabase
import com.abrahamlay.movielicious.kmm.movie.data.api.NowPlayingMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.api.PopularMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.api.TopRatedMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.datasource.MovieLocalDataSourceImpl
import com.abrahamlay.movielicious.kmm.movie.data.datasource.MovieRemoteDataSourceImpl
import com.abrahamlay.movielicious.kmm.movie.data.repository.MovieRepositoryImpl
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.datasource.MovieRemoteDataSource
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.repository.MovieLocalDataSource
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.repository.MovieRepository
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetNowPlayingCollection
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetPopularCollection
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetTopRatedCollection
import org.abrahamlay.movielicious.kmm.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
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
    single { DriverFactory(androidContext()) }
    single { createDatabase(get()) }
    single { get<AppDatabase>().movieQueries }
    single<MovieLocalDataSource> { MovieLocalDataSourceImpl(get()) }
    single { MovieRepositoryImpl(get(), get()) as MovieRepository }
    single { GetPopularCollection(get()) }
    single { GetNowPlayingCollection(get()) }
    single { GetTopRatedCollection(get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
}
