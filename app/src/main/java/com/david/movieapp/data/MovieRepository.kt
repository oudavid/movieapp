package com.david.movieapp.data

import android.util.Log
import com.google.gson.Gson

class MovieRepository {

    fun getMovieList(): List<Movie> {
        val gson = Gson()
        val moviesJson = getMovies().trim()
        val movies = gson.fromJson(moviesJson, MovieResponse::class.java)
        return movies.movies()
    }

    fun getDetail(movie: String): MovieDetail {
        val gson = Gson()
        val moviesJson = getMovieDetail(movie).trim()
        val movieDetailResponse = gson.fromJson(moviesJson, MovieDetailResponse::class.java)
        Log.d("test", movieDetailResponse.movieDetail.actors().toString())
        return movieDetailResponse.movieDetail
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    private external fun getMovies(): String
    private external fun getMovieDetail(movie: String): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
