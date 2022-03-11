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
import com.example.ryanair.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(),
    AdapterView.OnItemSelectedListener {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSearchBinding.inflate(inflater).run {
            lifecycleOwner = viewLifecycleOwner

            viewModel = mainViewModel
            mainViewModel.search.observe(viewLifecycleOwner) { search ->
                if (!mainViewModel.error && search ) {
                    findNavController().navigate(
                        SearchFragmentDirections.actionSearchFragmentToResultFragment()
                    )
                }
            }

            searchButton.setOnClickListener {
                mainViewModel.search()
            }

            originSpinner.apply {
                val newAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.spinner_item,
                    mainViewModel.stationsForSpinner
                )
                adapter = newAdapter
                onItemSelectedListener = this@SearchFragment

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
                onItemSelectedListener = this@SearchFragment

                mainViewModel.filters.observeOnce(viewLifecycleOwner) {
                    val spinnerDefaultPosition = newAdapter.getPosition(mainViewModel.defaultDestination)
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
    observe(owner, object: Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer(value)
        }
    })
}

