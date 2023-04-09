package com.example.e_montaysta.helpers.Interfaces

interface ISharedPreferencesHelper {
    fun get(key : String) : String?
    fun set(key : String, value : String? )
}