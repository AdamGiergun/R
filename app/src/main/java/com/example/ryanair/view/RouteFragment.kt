package com.example.ryanair.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.ryanair.databinding.FragmentRouteBinding
import com.example.ryanair.viewModel.RouteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RouteFragment : Fragment() {

    @Inject
    lateinit var routeViewModel: RouteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRouteBinding.inflate(inflater).run {
            lifecycleOwner = viewLifecycleOwner
            routeViewModel.also { rvm ->
                val args: RouteFragmentArgs by navArgs()
                val filters = args.filters
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