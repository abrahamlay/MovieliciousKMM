package com.abrahamlay.movielicious.kmm.movie.domain.model

//@Parcelize
//@Entity
data class Movie(
    var voteCount: Int,
//    @PrimaryKey
    var id: Int,
    var video: Boolean,
    var voteAverage: Double,
    var title: String?,
    var popularity: Double,
    var posterPath: String?,
    var originalLanguage: String?,
    var originalTitle: String?,
    var genreIds: List<Int>,
    var backdropPath: String?,
    var adult: Boolean,
    var overview: String?,
    var releaseDate: String?
)
//    : Parcelable