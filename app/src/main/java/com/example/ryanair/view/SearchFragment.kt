package com.example.ryanair.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ryanair.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

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
            root
        }
    }
}