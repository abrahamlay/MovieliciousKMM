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
    @StateObject var viewModel = HomeViewModel()

    var body: some View {
        VStack(spacing: 0) {
            Toolbar()
            ScrollView {
                VStack(spacing: 16) {
                    MovieSection(title: "Now Playing Movies").environmentObject(viewModel)
                    MovieSection(title: "Top Rated Movies").environmentObject(viewModel)
                    MovieSection(title: "Popular Movies").environmentObject(viewModel)
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
    @EnvironmentObject var viewModel: HomeViewModel
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(title)
                .font(.title2)
                .padding(.horizontal)
                .padding(.vertical)
            switch viewModel.state {
            case .idle:
                Color.clear.onAppear { viewModel.fetchMovies() }
            case .loading:
                LoadingView()
            case .loaded(let movies):
                HorizontalMovieList(movies: movies)
                    .frame(height: 300)  // ← Add fixed height
            case .error(let message):
                ErrorView(error: message) {
                    viewModel.fetchMovies()
                }
            }
        }
    }
}

struct HorizontalMovieList: View {
    let movies: [Movie]
    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: 16) {
                ForEach(movies, id: \.id) { movie in
                    MovieCard(movie: movie)
                }
            }.padding(.vertical)
        }
    }
}

struct MovieCard: View {
    let movie: Movie
    
    var body: some View {
        VStack(alignment: .center, spacing: 8) {
            Button(action: { print(movie.title ?? "unknown title") }) {
                // Image view
                Group {
                    if let imageUrl = formatImageUrl(movie.posterPath ?? ""),
                       let url = URL(string: imageUrl) {
                        KFImage(url)
                            .resizable()
                            .aspectRatio(contentMode: .fill)
                            .frame(width: 180, height: 270)  // ← Fixed size
                            .clipped()
                    } else {
                        Image(systemName: "photo")
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(width: 180, height: 270)
                            .foregroundColor(.gray)
                    }
                }
                .cornerRadius(8)
                .shadow(radius: 4)
            }
            
            // Text content
            VStack(spacing: 4) {
                Text(movie.title ?? "unknown title")
                    .font(.headline)
                    .lineLimit(1)
                    .frame(width: 180)
                
                HStack {
                    Image(systemName: "star.fill")
                        .foregroundColor(.yellow)
                    Text(String(format: "%.1f", movie.voteAverage))
                }
                .font(.caption)
            }
        }
        .padding(8)
    }
    
    func formatImageUrl(_ path: String) -> String? {
        if path.isEmpty {
            return ""
        } else if path.hasPrefix("http") {
            return path
        } else {
            return "https://image.tmdb.org/t/p/w500\(path)"
        }
    }
}


struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}
