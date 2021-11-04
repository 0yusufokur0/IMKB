package com.resurrection.imkb.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.resurrection.imkb.R

@BindingAdapter("loadImageFromUrl")
fun ImageView.loadImage(imageUrl: String?) {

    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 10f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.setArrowDimensions(30f, 30f)
    circularProgressDrawable.start()

    imageUrl?.let {
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(25))
        Glide.with(this)
            .load(imageUrl)/*.override(500,750)*/
            .placeholder(circularProgressDrawable)
            //.error(R.drawable.image_not_found)  // any image in case of error
            .apply(requestOptions)
            .into(this)
    }
}

