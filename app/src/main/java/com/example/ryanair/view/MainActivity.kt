package com.example.ryanair.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import com.example.ryanair.R
import com.example.ryanair.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityMainBinding.inflate(layoutInflater).run {
            setContentView(root)
        }

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setIcon(R.drawable.ic_logo)
            title = ""
        }

        val navController =
            (supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment)
                .navController

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        mainViewModel.stations.observe(this@MainActivity) {
            navController.navigate(
                StartFragmentDirections.actionStartFragmentToSearchFragment()
            )
        }
        mainViewModel.search.observe(this@MainActivity) {
            if (it && !mainViewModel.error ) {
                navController.navigate(
                    SearchFragmentDirections.actionSearchFragmentToResultFragment()
                )
            }
        }
    }
}