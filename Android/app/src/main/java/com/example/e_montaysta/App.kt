package com.example.e_montaysta

import DI.appModule
import Helpers.Interfaces.ISharedPreferencesHelper
import Helpers.SharedPreferencesHelper
import android.app.Application
import androidx.preference.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    override fun onCreate() {
        super.onCreate()

        val preferencesModule = module {
            single<ISharedPreferencesHelper> { SharedPreferencesHelper(PreferenceManager.getDefaultSharedPreferences(applicationContext)) }
        }

        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(DI.appModule, preferencesModule)
        }
        // Required initialization logic here!
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
//    override fun onConfigurationChanged ( newConfig : Configuration ) {
//        super.onConfigurationChanged(newConfig)
//    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
//    override fun onLowMemory() {
//        super.onLowMemory()
//    }
}