package com.example.e_montazysta.helpers.Interfaces

interface ISharedPreferencesHelper {
    fun get(key : String) : String?
    fun set(key : String, value : String? )
}