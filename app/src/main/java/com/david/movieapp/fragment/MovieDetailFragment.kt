package com.david.movieapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.david.movieapp.viewmodel.MovieViewModel
import com.david.movieapp.R
import com.david.movieapp.adapter.ActorAdapter
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieDetailFragment : Fragment() {

    private val viewModel: MovieViewModel by activityViewModels()

    private lateinit var actorListAdapter: ActorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        back.setOnClickListener {
            activity?.onBackPressed()
        }

        actorListAdapter = ActorAdapter(requireActivity())
        (actor_recycler_view as RecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = actorListAdapter
            isNestedScrollingEnabled = false
        }
        bindViews()
    }

    private fun bindViews() {
        val detail = viewModel.getCurrentMovieDetail().value
        detail?.let{
            actorListAdapter.setLists(detail.actors())
            movie_name.text = detail.name
            movie_score.text = detail.score.toString()
            movie_description.text = detail.description
        }
    }
}