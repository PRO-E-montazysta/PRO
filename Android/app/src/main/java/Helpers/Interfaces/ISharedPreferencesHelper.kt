package Helpers.Interfaces

interface ISharedPreferencesHelper {
    fun get(key : String) : String?
    fun set(key : String, value : String? )
}