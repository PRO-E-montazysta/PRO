package com.example.e_montaysta

import Controllers.Interfaces.IAuthController
import Helpers.Interfaces.ISharedPreferencesHelper
import Helpers.SharedPreferencesHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.widget.Button
import com.example.e_montaysta.ui.login.LoginActivity
import org.koin.android.ext.android.inject
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {

    private val authController: IAuthController by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val buttonClick = findViewById<Button>(R.id.button_start)
        buttonClick.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
