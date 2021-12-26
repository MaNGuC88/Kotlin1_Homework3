package com.example.kotlin1_homework3.extensions

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load (uri: String) {
    Glide.with(this)
        .load(uri)
        .centerCrop()
        .into(this)
}