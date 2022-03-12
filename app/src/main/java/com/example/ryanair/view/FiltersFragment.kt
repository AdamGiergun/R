package com.example.ryanair.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
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
                mainViewModel.filters.value?.let { filters ->
                    if (!mainViewModel.error) {
                        findNavController().navigate(
                            FiltersFragmentDirections.actionFiltersFragmentToRouteFragment(filters)
                        )
                    }
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

                mainViewModel.filters.observeOnce(viewLifecycleOwner) {
                    val spinnerDefaultPosition = newAdapter.getPosition(mainViewModel.defaultOrigin)
                    setSelection(spinnerDefaultPosition)
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

                mainViewModel.filters.observeOnce(viewLifecycleOwner) {
                    val spinnerDefaultPosition =
                        newAdapter.getPosition(mainViewModel.defaultDestination)
                    setSelection(spinnerDefaultPosition)
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

private fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, object : Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer(value)
        }
    })
}