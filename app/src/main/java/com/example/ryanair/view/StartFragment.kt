package com.example.ryanair.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ryanair.R
import com.example.ryanair.databinding.FragmentStartBinding
import com.example.ryanair.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentStartBinding.inflate(inflater).run {
            ObjectAnimator.ofFloat(loadingImg, "x", 1000f, -1000f).apply {
                duration = 2000
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.RESTART
                interpolator = LinearInterpolator()
                start()
            }
            mainViewModel.errorText.observe(viewLifecycleOwner) {
                if (it != null) {
                    loadingImg.visibility = View.GONE
                    errorInfo.apply {
                        visibility = View.VISIBLE
                        text = getString(R.string.error_internet, it)
                    }
                    retryButton.visibility = View.VISIBLE
                }
            }
            mainViewModel.stationsInitialized.observe(viewLifecycleOwner) {
                if (it) {
                    findNavController().navigate(
                        StartFragmentDirections.actionStartFragmentToFiltersFragment()
                    )
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