package com.example.e_montazysta.helpers

import android.content.SharedPreferences
import com.example.e_montazysta.helpers.Interfaces.ISharedPreferencesHelper

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