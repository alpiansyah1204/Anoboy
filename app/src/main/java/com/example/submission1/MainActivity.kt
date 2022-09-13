package com.example.submission1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.submission1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigationSetup()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun navigationSetup() {
        val navController =
            binding.fragmentContainerMain.getFragment<NavHostFragment>().navController
        val navView = binding.navViewMain
        binding.apply {
            binding.bottomNavMain.setItemSelected(navController.graph.startDestinationId)
            bottomNavMain.setOnItemSelectedListener { itemId ->
                navView.selectedItemId = itemId
            }
        }
        NavigationUI.setupWithNavController(navView, navController)
    }
}