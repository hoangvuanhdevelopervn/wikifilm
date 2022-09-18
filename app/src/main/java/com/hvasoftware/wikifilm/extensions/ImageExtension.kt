package com.hvasoftware.wikifilm.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hvasoftware.wikifilm.R
import kotlin.random.Random


internal fun ImageView.setUrl(url: String) {
    Glide.with(this.context).load(url)
        .thumbnail(Glide.with(context).load(R.drawable.loading_gif))
        .into(this)
}

internal fun getRandomImage(): String {
    return "https://picsum.photos/200/300?random=${Random(10000)}"
}
