package com.example.ryanair.view

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.ryanair.R

@BindingAdapter("onTextChanged")
fun View.onTextChanged(newText: String?) {
    if (newText != null) {
        visibility = if (id == R.id.loading_img) {
            View.GONE
        } else {
            (this as TextView).text = newText
            View.VISIBLE
        }
    }
}