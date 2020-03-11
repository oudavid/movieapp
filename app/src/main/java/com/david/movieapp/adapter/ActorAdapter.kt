package com.david.movieapp.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.david.movieapp.R
import com.david.movieapp.data.Actor
import kotlinx.android.synthetic.main.actor_item.view.*
import com.bumptech.glide.Glide

class ActorAdapter internal constructor(
    val context: Activity
) : RecyclerView.Adapter<ActorAdapter.ListViewHolder>() {

    private var actors = emptyList<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.actor_item, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {
        viewHolder.bind(actors[position])
    }

    internal fun setLists(actors: List<Actor>) {
        this.actors = actors
        notifyDataSetChanged()
    }

    override fun getItemCount() = actors.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.actor_name
        private val age: TextView = itemView.actor_age
        private val image: ImageView = itemView.actor_image

        fun bind(actor: Actor) {
            name.text = actor.name
            age.text = actor.age.toString()

            Glide.with(context)
                .load(actor.imageUrl)
                .placeholder(R.drawable.ic_face_placeholder_24px)
                .into(image)
        }
    }
}





