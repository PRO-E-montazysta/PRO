package com.example.e_montazysta.helpers

import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper
import android.content.SharedPreferences

class SharedPreferencesHelper(private val sharedPreferences: SharedPreferences) :
    ISharedPreferencesHelper {
    override fun get(key : String) : String? {
        return sharedPreferences.getString(key, null)
    }

    override fun set(key : String, value : String? ) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
}