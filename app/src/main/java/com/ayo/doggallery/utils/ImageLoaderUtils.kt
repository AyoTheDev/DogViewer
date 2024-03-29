package com.ayo.doggallery.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.ayo.doggallery.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import timber.log.Timber

object ImageLoaderUtils {

    private const val BASE_IMG_URL = "https://cdn2.thedogapi.com/images/%s.jpg"

    fun loadImage(context: Context?, imgId: String?, imageView: ImageView) {
        val imgUrl = String.format(BASE_IMG_URL, imgId)
        context?.let { ctx ->
            Glide.with(ctx)
                .load(imgUrl)
                .apply(requestOptions)
                .listener(requestListener)
                .into(imageView)
        }
    }

    private val requestOptions = RequestOptions()
        .error(R.drawable.ic_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .override(300, 600)

    private val requestListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?, model: Any?, target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            Timber.e(e)
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

    }
}
