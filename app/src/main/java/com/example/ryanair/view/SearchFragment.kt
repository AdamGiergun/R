package com.example.ryanair.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ryanair.R
import com.example.ryanair.databinding.FragmentSearchBinding

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
            searchButton.setOnClickListener {
                mainViewModel.search()
            }
            originSpinner.apply {
                val newAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    mainViewModel.getStationsForSpinner()
                )
                adapter = newAdapter
                val spinnerDefaultPosition = newAdapter.getPosition("Ireland, Dublin, DUB")
                setSelection(spinnerDefaultPosition)
                onItemSelectedListener = this@SearchFragment
            }
            root
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.origin_spinner -> mainViewModel.setOrigin(position)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}

