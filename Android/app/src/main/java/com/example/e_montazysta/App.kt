package com.example.e_montazysta

import com.example.e_montazysta.di.appModule
import com.example.e_montazysta.data.dataModule
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import com.example.e_montazysta.helpers.SharedPreferencesHelper
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import com.example.e_montazysta.ui.uiModule
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
            modules(preferencesModule, uiModule, dataModule)
        }
     // Required initialization logic here!
    }

}

fun ViewGroup.inflateLayout(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}