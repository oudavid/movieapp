package com.david.movieapp.data

data class Movie(
    val name: String,
    val lastUpdated: Int
)

data class MovieResponse(
    private val movies: List<Movie>
) {
    fun movies(): List<Movie> {
        return movies.filterNotNull()
    }
}

data class MovieDetail(
    val name: String,
    val score: Float,
    private val actors: List<Actor>,
    val description: String
) {
    fun actors(): List<Actor> {
        return actors.filterNotNull()
    }
}

data class MovieDetailResponse(
    val movieDetail: MovieDetail
)

data class Actor(
    val name: String,
    val age: Int,
    val imageUrl: String
)