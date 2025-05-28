//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Abraham Lay on 13/05/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import Shared

class HomeViewModel: ObservableObject {
    private let dataSource:  MovieRemoteDataSourceImpl
    private let movieRepository: MovieRepositoryImpl
    private let getPopularMoviesUseCase: GetPopularCollection
    
    
    init() {
        self.dataSource = MovieRemoteDataSourceImpl(
            popularMovieApi: PopularMovieApi(),
            topRatedMovieApi: TopRatedMovieApi(),
            nowPlayingMovieApi: NowPlayingMovieApi()
        )
        self.movieRepository = MovieRepositoryImpl(dataSourceImpl: dataSource)
        self.getPopularMoviesUseCase = GetPopularCollection(movieRepository: movieRepository)
    }
    @Published var movies: [Movie] = []

    func fetchMovies() {
        getPopularMoviesUseCase.invoke(callback: { [weak self] dataResult in
            if dataResult.status.isEqual(DataStatus.success) {
                let movieArray = dataResult.data as? [Movie] ?? []
                    self?.movies = movieArray
            } else if dataResult.status.isEqual(DataStatus.loading) {
                print("Loading…")
            } else if dataResult.status.isEqual(DataStatus.error) {
                print("Error: \(dataResult.errorMessage)")

            }
        })
        
        // Dummy data atau KMM interop di sini
//        self.movies = [
//            Movie(voteCount: Int32(7.4), id: 1, video: false, voteAverage: 7.4, title: "Avenger", popularity: 7.4, posterPath: "/yFHHfHcUgGAxziP1C3lLt0q2T4s.jpg", originalLanguage: "en", originalTitle: "Avenger", genreIds: [10751,14], backdropPath: "/2Nti3gYAX513wvhp8IiLL6ZDyOm.jpg", adult: true, overview: "real Avenger overview", releaseDate: "25-12-2025"),
//            Movie(voteCount: Int32(7.4), id: 2, video: false, voteAverage: 7.4, title: "Batman", popularity: 7.4, posterPath: "/yFHHfHcUgGAxziP1C3lLt0q2T4s.jpg", originalLanguage: "en", originalTitle: "Batman", genreIds: [10751,14], backdropPath: "/2Nti3gYAX513wvhp8IiLL6ZDyOm.jpg", adult: true, overview: "real Batman overview", releaseDate: "25-12-2025"),
//            Movie(voteCount: Int32(7.4), id: 2, video: false, voteAverage: 7.4, title: "Spiderman", popularity: 7.4, posterPath: "/yFHHfHcUgGAxziP1C3lLt0q2T4s.jpg", originalLanguage: "en", originalTitle: "Spiderman", genreIds: [10751,14], backdropPath: "/2Nti3gYAX513wvhp8IiLL6ZDyOm.jpg", adult: true, overview: "real Spiderman overview", releaseDate: "25-12-2025"),
//        ]
    }
    
}
