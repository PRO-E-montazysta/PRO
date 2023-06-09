package com.example.e_montazysta.ui.activities

import ThemeHelper
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.e_montazysta.R
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.example.e_montazysta.databinding.ActivitySettingsBinding
import com.google.android.material.switchmaterial.SwitchMaterial
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService

class SettingsActivity : Fragment() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var themeHelper: ThemeHelper
    private val notificationId = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivitySettingsBinding.inflate(layoutInflater, container, false)

        themeHelper = ThemeHelper(requireContext())
        binding.toolbar.title = "Ustawienia"

        // Retrieve the reference to the SwitchMaterial widget
        val notificationsSwitch: SwitchMaterial = binding.notificationsSwitch

        // Add listener to handle switch state changes
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Enable notifications
                // Create a notification channel
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channelId = "settingsChannel"
                    val channelName = "Settings Notifications Channel"
                    val channelDescription = "This is a notification channel within settings screen"

                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                    val channel = NotificationChannel(channelId, channelName, importance).apply {
                        description = channelDescription
                    }

                    // Register the channel with the system
                    val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                }

                // Create and send a sample notification
                val notificationBuilder = NotificationCompat.Builder(requireContext(), "settingsChannel")
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setContentTitle("E-montażysta: Ustawienia")
                    .setContentText("Włączono powiadomienia")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                val notificationManager = NotificationManagerCompat.from(requireContext())
                notificationManager.notify(notificationId, notificationBuilder.build())
            } else {
                // Cancel any existing notifications
                val notificationManager = NotificationManagerCompat.from(requireContext())
                notificationManager.cancel(notificationId)
            }
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
        return binding.root
    }
}