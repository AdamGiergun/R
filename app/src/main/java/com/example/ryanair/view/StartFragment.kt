package com.example.ryanair.view

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ryanair.R
import com.example.ryanair.databinding.FragmentStartBinding
import com.example.ryanair.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StartFragment : Fragment() {

    @Inject
    lateinit var mainViewModel: MainViewModel

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