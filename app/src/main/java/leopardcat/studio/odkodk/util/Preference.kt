package leopardcat.studio.odkodk.util

import android.content.Context
import android.content.SharedPreferences


class Preference(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    //date
    fun setDate(key: String, date: String) {
        prefs.edit().putString(key, date).apply()
    }
    fun getDate(key: String): String {
        return prefs.getString(key, "2023-12-23").toString()
    }

    //age
    fun setAge(key: String, age: Int) {
        prefs.edit().putInt(key, age).apply()
    }
    fun getAge(key: String): Int {
        return prefs.getInt(key, 8888)
    }

    //sex
    fun setSex(key: String, sex: Int) {
        prefs.edit().putInt(key, sex).apply()
    }
    fun getSex(key: String): Int {
        return prefs.getInt(key, 8888)
    }

    //library
    fun setLibrary(key: String, library: Int) {
        prefs.edit().putInt(key, library).apply()
    }
    fun getLibrary(key: String): Int {
        return prefs.getInt(key, 8888)
    }

    //library name
    fun setLibraryName(key: String, name: String) {
        prefs.edit().putString(key, name).apply()
    }
    fun getLibraryName(key: String): String {
        return prefs.getString(key, "").toString()
    }

    //setting finish
    fun setFinish(key: String, finish: Boolean) {
        prefs.edit().putBoolean(key, finish).apply()
    }
    fun getFinish(key: String): Boolean {
        return prefs.getBoolean(key, false)
    }

    //Product Id
    fun setProduct(key: String, product: String) {
        prefs.edit().putString(key, product).apply()
    }
    fun getProduct(key: String): String {
        return prefs.getString(key, "").toString()
    }
}