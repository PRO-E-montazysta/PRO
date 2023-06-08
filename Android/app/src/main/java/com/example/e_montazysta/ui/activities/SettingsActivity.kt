package com.example.e_montazysta.ui.activities

import ThemeHelper
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import com.example.e_montazysta.R
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.e_montazysta.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var themeHelper: ThemeHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        themeHelper = ThemeHelper(this)

        // Toolbar na górze
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Settings"
            setDisplayHomeAsUpEnabled(true)
        }

        // Get the saved theme preference
        val savedTheme = themeHelper.getSavedThemePreference()

        // Set the initial checked state based on the saved theme preference
        when (savedTheme) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> binding.radioBtnSystem.isChecked = true
            AppCompatDelegate.MODE_NIGHT_NO -> binding.radioBtnLight.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES -> binding.radioBtnDark.isChecked = true
        }

        // Handle theme selection
        binding.radioGroupTheme.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioBtnSystem -> {
                    themeHelper.saveThemePreference(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    themeHelper.applyTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                R.id.radioBtnLight -> {
                    themeHelper.saveThemePreference(AppCompatDelegate.MODE_NIGHT_NO)
                    themeHelper.applyTheme(AppCompatDelegate.MODE_NIGHT_NO)
                }
                R.id.radioBtnDark -> {
                    themeHelper.saveThemePreference(AppCompatDelegate.MODE_NIGHT_YES)
                    themeHelper.applyTheme(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}