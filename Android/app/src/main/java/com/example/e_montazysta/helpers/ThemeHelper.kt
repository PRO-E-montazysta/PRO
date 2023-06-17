import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class ThemeHelper(private val context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun applyTheme(themeMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

    fun saveThemePreference(themeMode: Int) {
        editor.putInt(PREF_KEY_THEME, themeMode).apply()
    }

    fun getSavedThemePreference(): Int {
        return sharedPreferences.getInt(PREF_KEY_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    companion object {
        private const val PREF_KEY_THEME = "pref_key_theme"
    }
}
