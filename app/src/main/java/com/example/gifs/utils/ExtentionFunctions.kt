package com.example.gifs.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.gifs.R
import com.example.gifs.data.Gif
import com.example.gifs.ui.grid.FIXED_HEIGHT_KEY

fun ImageView.setUpImage(gif: Gif){
    Glide.with(this.context).load(gif.images[FIXED_HEIGHT_KEY]?.url)
        .placeholder(R.color.grey200).into(this)
    contentDescription = gif.title
}
