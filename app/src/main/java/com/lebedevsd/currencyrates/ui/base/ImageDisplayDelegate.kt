package com.lebedevsd.currencyrates.ui.base

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.lebedevsd.currencyrates.R
import javax.inject.Inject

interface ImageDisplayer {
    fun displayTo(image: Image, to: ImageView)
}

interface ImageDisplayDelegate : ImageDisplayer {

    fun suitsFor(image: Image): Boolean
}

class FlagNameImagesDisplayDelegate : ImageDisplayDelegate {

    override fun suitsFor(image: Image) = image is FlagNameImage

    override fun displayTo(image: Image, to: ImageView) {
        image as FlagNameImage

        val imageResource = toImageResource(to.context, image.flagName)
        val drawable = if (imageResource > 0) {
            ContextCompat.getDrawable(to.context, imageResource)
        } else {
            ContextCompat.getDrawable(to.context, R.drawable.ic_launcher_foreground)
        }

        Glide.with(to.context)
            .applyDefaultRequestOptions(image.getGlideRequestOptions(to.context.resources))
            .load(drawable)
            .into(to)
    }

    private fun toImageResource(context: Context, flagName: String): Int {
        return context.resources.getIdentifier(flagName, "drawable", context.packageName)
    }
}

class ImagesDisplayeDelegates @Inject constructor() : ImageDisplayer {
    protected val delegates = listOf(
        FlagNameImagesDisplayDelegate()
    )

    override fun displayTo(image: Image, to: ImageView) {
        delegates.first { delegate -> delegate.suitsFor(image) }
            .displayTo(image, to)
    }
}
