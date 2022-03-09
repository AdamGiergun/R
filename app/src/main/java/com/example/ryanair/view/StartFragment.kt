package com.example.ryanair.view

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ryanair.R
import com.example.ryanair.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentStartBinding.inflate(inflater).run {
            ResourcesCompat.getDrawable(resources, R.drawable.animatorvectordrawable, null).let {
                loadingImg.setImageDrawable(it)
                if (it is Animatable) it.start()
            }
            mainViewModel.errorText.observe(viewLifecycleOwner) {
                if (it != null) {
                    loadingImg.visibility = View.GONE
                    errorInfo.visibility = View.VISIBLE
                    retryButton.visibility = View.VISIBLE
                }
            }
            retryButton.setOnClickListener {
                mainViewModel.initStations()
                loadingImg.visibility = View.VISIBLE
                errorInfo.visibility = View.GONE
                retryButton.visibility = View.GONE
            }
            root
        }
    }
}