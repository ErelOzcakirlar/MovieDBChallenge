package com.erel.movies.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("url")
fun ImageView.load(url: String?) {
    Picasso.get().load(url).fit().centerCrop().into(this)
}