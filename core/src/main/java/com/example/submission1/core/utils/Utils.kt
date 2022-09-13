package com.example.submission1.core.utils

import android.widget.ImageView
import com.example.submission1.core.R
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.poster_placeholder)
        .into(this)
}