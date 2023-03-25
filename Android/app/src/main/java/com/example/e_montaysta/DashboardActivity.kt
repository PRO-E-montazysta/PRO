package com.example.e_montaysta

import Helpers.Interfaces.ISharedPreferencesHelper
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.e_montaysta.R
import com.example.e_montaysta.databinding.ActivityDashboardBinding
import org.koin.android.ext.android.inject

class DashboardActivity : AppCompatActivity() {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesHelper.get("lama")
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setupWithNavController(navController)
    }
}
