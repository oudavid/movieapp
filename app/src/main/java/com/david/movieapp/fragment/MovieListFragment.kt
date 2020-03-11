package com.david.movieapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.david.movieapp.viewmodel.MovieViewModel
import com.david.movieapp.R
import com.david.movieapp.data.Movie
import com.david.movieapp.adapter.MovieAdapter
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : Fragment(),
    MovieAdapter.OnItemClickListener {

    private val viewModel: MovieViewModel by activityViewModels()

    private lateinit var listAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onResume() {
        super.onResume()
        viewModel.setCurrentMovie(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovies().observe(viewLifecycleOwner, Observer<List<Movie>> { lists ->
            lists?.let { listAdapter.setLists(it) }
        })

        // set up the recycler view
        listAdapter = MovieAdapter(requireContext(), this)

        (list_recycler_view as RecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun onItemClicked(movie: Movie) {
        viewModel.setCurrentMovie(movie)
    }
}