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
//            Movie(id: 1, title: "Batman", posterPath: "/xyz.jpg", voteAverage: 8.5),
//            Movie(id: 2, title: "Superman", posterPath: "/abc.jpg", voteAverage: 7.4)
//        ]
    }
    
}
