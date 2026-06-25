package com.gazlaws.codeboard

import org.json.JSONObject

object SettingsValidator {

    private val VALID_KEYS = setOf(
        "FIRST_START", "sound", "vibrate", "vibrate_ms", "bg_colour_picker",
        "fg_colour_picker", "size_portrait", "size_landscape", "font_size",
        "preview", "borders", "input_symbols_main", "input_symbols_main_2",
        "input_symbols_main_bottom", "input_symbols_sym", "input_symbols_sym_2",
        "input_symbols_sym_3", "input_symbols_sym_4", "input_symbols_sym_bottom",
        "navbar", "navbar_dark", "layout", "theme", "custom_theme",
        "pin1", "pin2", "pin3", "pin4", "pin5", "pin6", "pin7",
        "notification", "top_row_actions"
    )

    fun validateAndParse(content: String): Pair<JSONObject?, String?> {
        return try {
            val jsonObject = JSONObject(content)

            var matchCount = 0
            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                if (VALID_KEYS.contains(keys.next())) {
                    matchCount++
                }
            }

            if (matchCount == 0) {
                null to "This is not a valid settings file"
            } else {
                jsonObject to null
            }
        } catch (e: Exception) {
            null to "Invalid JSON format: ${e.localizedMessage}"
        }
    }

    fun isKeyValid(key: String): Boolean = VALID_KEYS.contains(key)
}
