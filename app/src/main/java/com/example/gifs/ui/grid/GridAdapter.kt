package com.example.gifs.ui.grid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gifs.R
import com.example.gifs.data.Gif
import com.example.gifs.utils.setUpImage
import kotlinx.android.synthetic.main.item_grid_image.view.*

internal const val FIXED_HEIGHT_KEY = "fixed_height"

class GridAdapter(val clickListener: (Gif) -> Unit) :
    PagedListAdapter<Gif, GridAdapter.GifViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GifViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_grid_image, parent, false
        )
    )

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gifImageView = itemView.gifImage

        fun bind(gif: Gif) {
            itemView.setOnClickListener {
                clickListener.invoke(gif)
            }
            gifImageView.setUpImage(gif)
        }
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<Gif>() {
    override fun areItemsTheSame(oldItem: Gif, newItem: Gif) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Gif, newItem: Gif) = oldItem == newItem
}
