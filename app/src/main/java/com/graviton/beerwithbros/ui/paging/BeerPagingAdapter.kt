package com.graviton.beerwithbros.ui.paging

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.graviton.beerwithbros.model.Beer
import com.graviton.beerwithbros.ui.BeerViewHolder

class BeerPagingAdapter : PagingDataAdapter<Beer, BeerViewHolder>(
    diffCallback
) {
    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        getItem(position)?.let { holder.bindTo(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        return BeerViewHolder(parent)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Beer>() {
            override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
                return oldItem.imageUrl == newItem.imageUrl
            }

        }
    }
}