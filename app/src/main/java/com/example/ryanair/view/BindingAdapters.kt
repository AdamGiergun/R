package com.example.ryanair.view

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.ryanair.R

@BindingAdapter("onErrorTextChanged")
fun TextView.onErrorTextChanged(errorText: String?) {
    errorText?.let {
        text = resources.getString(R.string.error)
        Log.d("ERROR_TEXT", it)
    }
}