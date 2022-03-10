package com.example.ryanair.view

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.ryanair.R

@BindingAdapter("onTextChanged")
fun TextView.onTextChanged(newText: String?) {
    newText?.let { text = it }
}

@BindingAdapter("onErrorTextChanged")
fun TextView.onErrorTextChanged(errorText: String?) {
    errorText?.let { text = resources.getString(R.string.error) }
    // TODO do sth with errorText
}