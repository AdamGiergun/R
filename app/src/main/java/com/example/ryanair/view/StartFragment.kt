package com.example.ryanair.view

import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.example.ryanair.R
import com.example.ryanair.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentStartBinding.inflate(inflater).run {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            ResourcesCompat.getDrawable(resources, R.drawable.animatorvectordrawable, null).let {
                loadingImg.setImageDrawable(it)
                if (it is Animatable) it.start()
            }
            root
        }
    }
}