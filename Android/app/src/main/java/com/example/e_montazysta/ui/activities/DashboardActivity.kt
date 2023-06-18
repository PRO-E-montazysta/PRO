package com.example.e_montazysta.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.e_montazysta.R
import com.example.e_montazysta.databinding.ActivityDashboardBinding
import com.example.e_montazysta.ui.viewmodels.QRScanViewModel
import com.google.android.gms.common.api.OptionalModuleApi
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
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

        navView.setOnItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.navigation_qrscan) {
                setupScanner()
            }
            true
        }

        navController = findNavController(R.id.nav_host_fragment_activity_dashboard)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setupWithNavController(navController)



    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun setupScanner() {
        scannerOptions = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE
            )
            .build()

        moduleInstallClient = ModuleInstall.getClient(this)
        scanner = GmsBarcodeScanning.getClient(this, scannerOptions)

        installApiModule(moduleInstallClient, scanner)
        scanner.startScan()?.addOnSuccessListener { barcode ->
            val code = barcode?.rawValue
            when (code?.first()) {
                'E' -> {
                    qrScanViewModel.getElementByCode(code)?.let {
                        navController.navigate(R.id.elementDetailFragment)
                    }
                }

                'T' -> {
                    qrScanViewModel.getToolByCode(code)?.let {
                    }
                }

                else -> {
                    Toast.makeText(
                        this,
                        "Niepoprawny kod QR!\nWartość: $code",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }?.addOnCanceledListener {

        }?.addOnFailureListener { e ->
            Toast.makeText(
                this,
                "Trwa inicjalizacja skanera, spróbuj ponownie.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun installApiModule(
        moduleInstallClient: ModuleInstallClient,
        module: OptionalModuleApi
    ) {
        moduleInstallClient
            .areModulesAvailable(module)
            .addOnSuccessListener {
                if (it.areModulesAvailable()) {
                    // Modules are present on the device...
                } else {
                    val moduleInstallRequest =
                        ModuleInstallRequest.newBuilder()
                            .addApi(module)
                            .build()
                    moduleInstallClient
                        .installModules(moduleInstallRequest)
                        .addOnSuccessListener {
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
                        }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
            }
    }
}