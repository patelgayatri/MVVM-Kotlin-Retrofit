package com.techand.sampletest.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(url: String?) {
    if (url != null && url.isNotBlank()) {

        Picasso.get()
            .load(url)
            .into(this, object : Callback {
                override fun onSuccess() {
                    Log.d("Adapter", "success")
                }

                override fun onError(e: Exception?) {
                    Log.d("Adapter", "error")
                }
            })
    }
}