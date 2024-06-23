package org.abrahamlay.movielicious.kmm.di.movie

import com.abrahamlay.movielicious.kmm.movie.data.api.NowPlayingMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.api.PopularMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.api.TopRatedMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.datasource.MovieDataSourceImpl
import com.abrahamlay.movielicious.kmm.movie.data.datasource.remote.MovieRemoteDataSourceImpl
import com.abrahamlay.movielicious.kmm.movie.data.repository.MovieRepositoryImpl
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.datasource.MovieDataSource
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.repository.MovieRepository
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetNowPlayingCollection
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetPopularCollection
import com.abrahamlay.movielicious.kmm.movie.domain.usecase.GetTopRatedCollection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataDomainModule {
    @Provides
    fun provideMovieRemoteDataSource(): MovieDataSource.Remote = MovieRemoteDataSourceImpl(
        PopularMovieApi(),
        TopRatedMovieApi(),
        NowPlayingMovieApi()
    )

    @Provides
    fun provideMovieDataSource(remote: MovieDataSource.Remote): MovieDataSource =
        MovieDataSourceImpl(remote)

    @Provides
    fun provideMovieRepository(movieDataSource: MovieDataSource): MovieRepository =
        MovieRepositoryImpl(movieDataSource)

    @Provides
    fun provideGetPopularCollection(movieRepository: MovieRepository): GetPopularCollection =
        GetPopularCollection(movieRepository)

    @Provides
    fun provideGetTopRatedCollection(movieRepository: MovieRepository): GetTopRatedCollection =
        GetTopRatedCollection(movieRepository)

    @Provides
    fun provideGetNowPlayingCollection(movieRepository: MovieRepository): GetNowPlayingCollection =
        GetNowPlayingCollection(movieRepository)
}