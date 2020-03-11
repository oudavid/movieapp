package com.david.movieapp.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.david.movieapp.R
import com.david.movieapp.data.MovieDetail
import com.david.movieapp.fragment.MovieDetailFragment
import com.david.movieapp.fragment.MovieListFragment
import com.david.movieapp.replaceFragment
import com.david.movieapp.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.supportActionBar?.hide()

        displayMovieListFragment()
        viewModel.getCurrentMovieDetail().observe(this, Observer<MovieDetail> {
            it?.let {
                displayMovieDetailFragment()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if(viewModel.getCurrentMovieDetail().value == null) {
            displayMovieListFragment()
        } else{
            displayMovieDetailFragment()
        }
    }

    fun displayMovieListFragment() {
        replaceFragment(R.id.fragment_container, MovieListFragment())
    }

    fun displayMovieDetailFragment() {
        replaceFragment(R.id.fragment_container, MovieDetailFragment())

    }
}
