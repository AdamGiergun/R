package com.example.ryanair.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ryanair.databinding.FragmentRouteBinding
import com.example.ryanair.viewModel.RouteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RouteFragment : Fragment() {

    private val routeViewModel: RouteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRouteBinding.inflate(inflater).run {
            lifecycleOwner = viewLifecycleOwner
            routeViewModel.also { rvm ->
                val filters = RouteFragmentArgs.fromBundle(requireArguments()).filters
                rvm.refreshRoute(filters)

                viewModel = rvm
                rvm.errorText.observe(viewLifecycleOwner) {
                    errorText.text = if (rvm.errorTextId > 0)
                        getString(rvm.errorTextId, rvm.errorText.value)
                    else
                        rvm.errorText.value
                }
            }
            root
        }
    }
}