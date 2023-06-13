package com.example.e_montazysta.ui.activities

import ThemeHelper
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
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
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.button.MaterialButton
import com.example.e_montazysta.data.model.CurrentUser
import com.example.e_montazysta.ui.fragments.dashboard.DashboardFragmentDirections
import com.example.e_montazysta.ui.notification.NotificationListViewModel
import com.example.e_montazysta.ui.viewmodels.SettingsViewModel
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.e_montazysta.ui.notification.NotificationListFragment
import com.example.e_montazysta.ui.viewmodels.DashboardViewModel
//import androidx.navigation.fragment.findNavController

class SettingsActivity : Fragment() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: DashboardViewModel by viewModel()
    private val notificationViewModel: NotificationListViewModel by viewModel()
   // private var isBackPressedFromDialog = false
    private lateinit var themeHelper: ThemeHelper
    private val notificationId = 1

    @com.google.android.material.badge.ExperimentalBadgeUtils
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivitySettingsBinding.inflate(inflater, container, false)

        themeHelper = ThemeHelper(requireContext())
        binding.toolbar.title = "Ustawienia"

        viewModel.getCurrentUser()
        viewModel.currentUser.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.userName.text = it.firstName + ' ' + it.lastName
                binding.companyName.text = it.companyName
                binding.profilePicture.load(it.profilePhotoUrl ?: "https://i.imgflip.com/3u04h5.jpg?a468072"){
                    transformations(CircleCropTransformation())
                }
            }
        })

        // TOOLBAR

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.notifications -> {
                    val action = SettingsActivityDirections.actionSettingsFragmentToNotificationListFragment()
                    view?.findNavController()?.navigate(action)
                    true
                }
                else -> {
                    Toast.makeText(context, "Błąd dzielenia przez ogórek", Toast.LENGTH_LONG).show()
                    false}

            }
        }

        val badgeDrawable = BadgeDrawable.create(requireContext())
        notificationViewModel.getNotification()
        notificationViewModel.notificationsNumberLiveData.observe(viewLifecycleOwner) {
                notificationNumber ->
            when(notificationNumber){
                0 -> BadgeUtils.detachBadgeDrawable(badgeDrawable, binding.toolbar, R.id.notifications)
                else -> {
                    badgeDrawable.number = notificationNumber
                    BadgeUtils.attachBadgeDrawable(badgeDrawable, binding.toolbar, R.id.notifications)
                }
            }

        }

        // Logout the User
        val logoutButton: MaterialButton = binding.logoutButton

        logoutButton.setOnClickListener{
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

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