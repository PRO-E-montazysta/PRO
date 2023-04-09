package com.example.e_montaysta.ui.activities

import com.example.e_montaysta.data.controllers.Interfaces.IAuthController
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.e_montaysta.R
import org.koin.android.ext.android.inject

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