package com.example.e_montazysta.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.e_montazysta.R
import com.example.e_montazysta.databinding.ActivityDashboardBinding
import com.example.e_montazysta.ui.viewmodels.QRScanViewModel
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DashboardActivity : AppCompatActivity(), KoinComponent {
    private lateinit var binding: ActivityDashboardBinding
    lateinit var scannerOptions: GmsBarcodeScannerOptions
    lateinit var moduleInstallClient: ModuleInstallClient
    lateinit var scanner: GmsBarcodeScanner
    lateinit var navController: NavController
    private val qrScanViewModel: QRScanViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setupWithNavController(navController)



    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}