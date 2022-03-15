package com.example.ryanair.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
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
            mainViewModel.run {
                viewModel = this
            }

            searchButton.setOnClickListener {
                if (mainViewModel.error.value != true) {
                    findNavController().navigate(
                        FiltersFragmentDirections.actionFiltersFragmentToRouteFragment(mainViewModel.filters.value)
                    )
                }
            }

            originSpinner.initialize()
            destinationSpinner.initialize()
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

    private fun AppCompatSpinner.initialize() {
        mainViewModel.filters.observeUntil(viewLifecycleOwner) { filters ->
            if (filters != null) {
                mainViewModel.stationsInitialized.observe(viewLifecycleOwner) { initialized ->
                    if (initialized) {
                        val newAdapter = ArrayAdapter(
                            requireContext(),
                            R.layout.spinner_item,
                            mainViewModel.stationsForSpinner
                        )
                        adapter = newAdapter
                        onItemSelectedListener = this@FiltersFragment
                        setSelection(
                            if (id == R.id.destination_spinner)
                                mainViewModel.defaultDestinationPosition
                            else
                                mainViewModel.defaultOriginPosition
                        )
                    }
                }
            }
        }
    }
}

private fun <T> LiveData<T>.observeUntil(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, object : Observer<T> {
        override fun onChanged(value: T) {
            if ((value is Boolean && value) || (value != null)) {
                removeObserver(this)
                observer(value)
            }
        }
    })
}