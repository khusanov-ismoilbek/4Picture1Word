package uz.gita.a4picture1word.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MyPreference @Inject constructor(@ApplicationContext context: Context) {
    private val preference = context.getSharedPreferences("PicsWord", Context.MODE_PRIVATE)

    var level: Int
        get() = preference.getInt("LEVEL", 0)
        set(value) = preference.edit().putInt("LEVEL", value).apply()
    var coins: Int
        get() = preference.getInt("COINS", 0)
        set(value) = preference.edit().putInt("COINS", value).apply()
    var music: Int
        get() = preference.getInt("MUSIC", 1)
        set(value) = preference.edit().putInt("MUSIC", value).apply()
    var sound: Int
        get() = preference.getInt("SOUND", 1)
        set(value) = preference.edit().putInt("SOUND", value).apply()
}