package com.david.movieapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.david.movieapp.R
import com.david.movieapp.data.Movie
import kotlinx.android.synthetic.main.list_item.view.*

class MovieAdapter internal constructor(
    context: Context,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var movies = emptyList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = inflater.inflate(R.layout.list_item, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {
        viewHolder.bind(movies[position], itemClickListener)
    }

    internal fun setLists(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun getItemCount() = movies.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.item_title
        private val lastUpdated: TextView = itemView.last_updated

        fun bind(movie: Movie, clickListener: OnItemClickListener) {

            title.text = movie.name
            lastUpdated.text = movie.lastUpdated.toString()
            itemView.setOnClickListener {
                clickListener.onItemClicked(movie)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(movie: Movie)
    }
}





