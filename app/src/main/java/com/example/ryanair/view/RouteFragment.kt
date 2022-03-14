package com.example.ryanair.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ryanair.databinding.FragmentRouteBinding
import com.example.ryanair.viewModel.RouteViewModel

class RouteFragment : Fragment() {

    private val routeViewModel: RouteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRouteBinding.inflate(inflater).run {
            lifecycleOwner = viewLifecycleOwner
            routeViewModel.also { rvm ->
                viewModel = rvm
                rvm.errorInfoId.observe(viewLifecycleOwner) {
                    val id = it ?: 0
                    errorText.text = if (id > 0)
                        getString(id, rvm.errorText.value)
                    else
                        rvm.errorText.value
                }
            }
            root
        }
    }
}