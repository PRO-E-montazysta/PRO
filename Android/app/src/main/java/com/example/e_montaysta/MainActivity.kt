package com.example.e_montaysta

import Controllers.Interfaces.IAuthController
import Helpers.Interfaces.ISharedPreferencesHelper
import Helpers.SharedPreferencesHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.widget.Button
import android.widget.TextView
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.ext.android.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {
    private val authController: IAuthController by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferencesModule = module {
            single<ISharedPreferencesHelper> { SharedPreferencesHelper(PreferenceManager.getDefaultSharedPreferences(applicationContext)) }
        }
        startKoin{
            androidLogger()
            androidContext(this@MainActivity)
            modules(DI.appModule, preferencesModule)
        }
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.loginButton)
        button.setOnClickListener {
            val text = authController.login("admin", "password")
            val textView : TextView = findViewById(R.id.label)
            textView.text = text
        }
    }
}