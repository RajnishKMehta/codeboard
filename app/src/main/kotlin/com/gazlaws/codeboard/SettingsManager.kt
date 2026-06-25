package com.gazlaws.codeboard

import android.content.Context
import androidx.preference.PreferenceManager
import org.json.JSONObject
import java.io.InputStream
import java.io.OutputStream

object SettingsManager {

    private const val MAX_FILE_SIZE = 512 * 1024 // 512KB is plenty for settings

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
        } catch (t: Throwable) {
            t.localizedMessage ?: "Unknown export error"
        } finally {
            try { outputStream.close() } catch (ignored: Exception) {}
        }
    }

    @JvmStatic
    fun importSettings(context: Context, inputStream: InputStream): String? {
        return try {
            val buffer = ByteArray(MAX_FILE_SIZE + 1)
            val bytesRead = inputStream.read(buffer)

            if (bytesRead > MAX_FILE_SIZE) {
                return "File too large (max 512KB)"
            }

            val content = if (bytesRead > 0) String(buffer, 0, bytesRead) else ""
            if (content.isBlank()) return "File is empty"

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
                    is Float -> editor.putFloat(key, value)
                    is String -> editor.putString(key, value)
                }
            }
            editor.apply()
            null
        } catch (t: Throwable) {
            t.localizedMessage ?: "Invalid JSON or read error"
        } finally {
            try { inputStream.close() } catch (ignored: Exception) {}
        }
    }
}
