package com.graviton.beerwithbros.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graviton.beerwithbros.R
import com.graviton.beerwithbros.model.Beer

class BeerViewHolder(private val parent: ViewGroup): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.fragment_beer_item, parent, false)
) {
    private val image = itemView.findViewById<ImageView>(R.id.image)
    private val name = itemView.findViewById<TextView>(R.id.name)
    private val desc = itemView.findViewById<TextView>(R.id.desc)

    fun bindTo(item: Beer) {
        item.imageUrl?.let {
            Glide.with(parent.context)
                .load(it)
                .fitCenter()
                .into(image)
        }
        item.name?.let {
            name.text = item.name
        }
        item.description?.let {
            desc.text = item.description
        }
    }
}