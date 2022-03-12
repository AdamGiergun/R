package com.example.ryanair.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ryanair.R
import com.example.ryanair.databinding.FragmentFiltersBinding
import com.example.ryanair.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FiltersFragment : Fragment(),
    AdapterView.OnItemSelectedListener {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFiltersBinding.inflate(inflater).run {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel

            searchButton.setOnClickListener {
                if (!mainViewModel.error) {
                    findNavController().navigate(
                        FiltersFragmentDirections.actionFiltersFragmentToRouteFragment()
                    )
                }
            }

            originSpinner.apply {
                val newAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.spinner_item,
                    mainViewModel.stationsForSpinner
                )
                adapter = newAdapter
                onItemSelectedListener = this@FiltersFragment

                mainViewModel.filtersInitialized.observe(viewLifecycleOwner) {
                    if (it) {
                        setSelection(mainViewModel.defaultOriginPosition)
                    }
                }
            }

            destinationSpinner.apply {
                val newAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.spinner_item,
                    mainViewModel.stationsForSpinner
                )
                adapter = newAdapter
                onItemSelectedListener = this@FiltersFragment

                mainViewModel.filtersInitialized.observe(viewLifecycleOwner) {
                    if (it) {
                        setSelection(mainViewModel.defaultDestinationPosition)
                    }
                }
            }
            root
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.origin_spinner -> mainViewModel.setOrigin(position)
            R.id.destination_spinner -> mainViewModel.setDestination(position)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}