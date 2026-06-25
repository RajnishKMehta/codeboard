package com.gazlaws.codeboard

import android.content.Context
import androidx.preference.PreferenceManager
import org.json.JSONObject
import java.io.InputStream
import java.io.OutputStream

object SettingsManager {

    /**
     * Exports all SharedPreferences to a JSON file.
     */
    @JvmStatic
    fun exportSettings(context: Context, outputStream: OutputStream): Boolean {
        return try {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val allPrefs = prefs.all
            val jsonObject = JSONObject()

            for ((key, value) in allPrefs) {
                jsonObject.put(key, value)
            }

            outputStream.bufferedWriter().use { writer ->
                writer.write(jsonObject.toString(4))
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Imports SharedPreferences from a JSON file.
     */
    @JvmStatic
    fun importSettings(context: Context, inputStream: InputStream): Boolean {
        return try {
            val content = inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(content)
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()

            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                val value = jsonObject.get(key)

                when (value) {
                    is Boolean -> editor.putBoolean(key, value)
                    is Int -> editor.putInt(key, value)
                    is Long -> editor.putLong(key, value)
                    is Double -> editor.putFloat(key, value.toFloat())
                    is String -> editor.putString(key, value)
                    // If it is something else, we ignore or log
                }
            }
            editor.apply()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
