package com.example.e_montazysta.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_montazysta.R
import com.example.e_montazysta.data.controllers.Interfaces.IAuthController
import com.google.android.material.appbar.MaterialToolbar
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val authController: IAuthController by inject()

    lateinit var topAppBar : MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val buttonClick = findViewById<Button>(R.id.button_start)
        buttonClick.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        topAppBar = findViewById(R.id.topAppBar)

        topAppBar.setNavigationOnClickListener {
            Toast.makeText(this, "Navigation Icon Clicked", Toast.LENGTH_SHORT).show()
        }

        topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.back -> {
                    Toast.makeText(this, "Back action clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.notifications -> {
                    Toast.makeText(this, "Notifications action clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> {
                    false
                }
            }
        }
    }
}