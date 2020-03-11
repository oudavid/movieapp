package com.david.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.david.movieapp.data.Movie
import com.david.movieapp.data.MovieDetail
import com.david.movieapp.data.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MovieRepository = MovieRepository()

    private val movies: MutableLiveData<List<Movie>> = MutableLiveData()
    
    private var currentMovieDetail: MutableLiveData<MovieDetail> = MutableLiveData()

    init {
        retrieveMovieList()
    }

    fun getCurrentMovieDetail(): MutableLiveData<MovieDetail> = currentMovieDetail

    fun getMovies(): LiveData<List<Movie>> = movies
    
    fun setCurrentMovie(movie: Movie?) {
        if (movie == null) {
            currentMovieDetail.value = null
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                currentMovieDetail.value = withContext(Dispatchers.IO) {
                    repository.getDetail(movie.name)
                }
            }
        }
    }

    private fun retrieveMovieList() {
        viewModelScope.launch(Dispatchers.Main) {
            movies.value = withContext(Dispatchers.IO) {
                repository.getMovieList()
            }
        }
    }
}
