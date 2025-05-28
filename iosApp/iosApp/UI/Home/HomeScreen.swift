//
//  HomeScreen.swift
//  iosApp
//
//  Created by Abraham Lay on 13/05/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import Shared
import Kingfisher

struct HomeScreen: View {
    @ObservedObject var viewModel = HomeViewModel()

    var body: some View {
        VStack(spacing: 0) {
            Toolbar()
            ScrollView {
                VStack(spacing: 16) {
                    MovieSection(title: "Popular Movies", movies: viewModel.movies)
                    Divider()
                    //                    MovieSection(title: "Popular Movies", movies: viewModel.movies)
                }
                .padding(.top, 16)
            }.refreshable {
                viewModel.fetchMovies()
            }.onAppear{
                viewModel.fetchMovies()
            }
        }
    }
}

struct MovieSection: View {
    let title: String
    let movies: [Movie]

    var body: some View {
        VStack(alignment: .leading) {
            Text(title)
                .font(.title2)
                .padding(.horizontal)

            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 16) {
                    ForEach(movies, id: \.id) { movie in
                        MovieCard(movie: movie)
                    }
                }
                .padding(.horizontal)
            }
        }
    }
}

struct MovieCard: View {
    let movie: Movie

    var body: some View {
        VStack(alignment: .center) {
            Button(action: {
                print(movie.title ?? "unknown title")
            }) {
                
                let image = movie.posterPath
                let imageUrl: String? = {
                    guard let image = image else { return "" }
                    if !image.contains("https://") && !image.contains("http://") {
                        return String(format: Constants.MOVIE_THUMBNAIL_BASE_URL_MEDIUM, image)
                    } else {
                        return image
                    }
                }()
                if let imageUrl = imageUrl,
                   let url = URL(string: imageUrl) {
                    KFImage(url)
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                } else {
                    // Tampilin placeholder atau image default
                    Image(systemName: "photo")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .foregroundColor(.gray)
                }
            }

            Text(movie.title ?? "unknown title")
                .font(.headline)
                .lineLimit(1)
                .frame(width: 180)
                .multilineTextAlignment(.center)

            HStack {
                Image(systemName: "star.fill")
                    .foregroundColor(.yellow)
                Text(String(format: "%.1f", movie.voteAverage))
            }
        }
    }

    func formatImageUrl(_ path: String) -> String {
        if path.hasPrefix("http") {
            return path
        } else {
            return String(format: "https://image.tmdb.org/t/p/w500%@", path)
        }
    }
}


struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}
