package com.lebedevsd.currencyrates.ui.base

import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.parcel.Parcelize

interface Image : Parcelable

@Parcelize
data class ImageTransformations(
    val circle: Boolean = false
) : Parcelable


interface TransformableImage {
    val transformations: ImageTransformations?

    fun getGlideTransformsArray(resources: Resources): Array<Transformation<Bitmap>> {
        return mutableListOf<Transformation<Bitmap>>().apply {
            if (transformations?.circle == true) {
                add(CircleCrop())
            }
        }.toTypedArray()
    }

    fun getGlideRequestOptions(resources: Resources): RequestOptions =
        RequestOptions().applyImageTransformations(resources, this)
}

internal fun RequestOptions.applyImageTransformations(
    resources: Resources,
    image: TransformableImage
): RequestOptions =
    apply {
        val transformations = image.getGlideTransformsArray(resources)
        if (transformations.isNotEmpty()) {
            transforms(*transformations)
        }
    }


@Parcelize
data class ResourceImage(
    @DrawableRes val drawableRes: Int,
    @ColorRes val colorRes: Int? = null,
    override val transformations: ImageTransformations = ImageTransformations(circle = true)
) : Image, TransformableImage

@Parcelize
data class FlagNameImage(
    val flagName: String,
    override val transformations: ImageTransformations = ImageTransformations(circle = true)
) : Image, TransformableImage

