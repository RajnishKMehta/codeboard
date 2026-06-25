package com.gazlaws.codeboard

import android.content.Context
import androidx.preference.PreferenceManager
import org.json.JSONObject
import java.io.InputStream
import java.io.OutputStream

object SettingsManager {

    /**
     * Exports all SharedPreferences to a JSON file.
     * Returns null on success, or error message on failure.
     */
    @JvmStatic
    fun exportSettings(context: Context, outputStream: OutputStream): String? {
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
            null
        } catch (e: Exception) {
            e.message ?: "Unknown export error"
        } finally {
            try { outputStream.close() } catch (ignored: Exception) {}
        }
    }

    /**
     * Imports SharedPreferences from a JSON file.
     * Returns null on success, or error message on failure.
     */
    @JvmStatic
    fun importSettings(context: Context, inputStream: InputStream): String? {
        return try {
            val content = inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(content)
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = prefs.edit()

            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                if (jsonObject.isNull(key)) continue

                val value = jsonObject.get(key)
                when (value) {
                    is Boolean -> editor.putBoolean(key, value)
                    is Int -> editor.putInt(key, value)
                    is Long -> editor.putLong(key, value)
                    is Double -> editor.putFloat(key, value.toFloat())
                    is String -> editor.putString(key, value)
                }
            }
            editor.apply()
            null
        } catch (e: Exception) {
            e.message ?: "Invalid JSON or read error"
        } finally {
            try { inputStream.close() } catch (ignored: Exception) {}
        }
    }
}
