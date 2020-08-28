package com.sunasterisk.anticovid_19.utils

import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.model.Global
import org.json.JSONArray
import org.json.JSONException

@Throws(JSONException::class)
inline fun <reified T> String.parseJsonArray() = with(JSONArray(this)) {
    List(length()) {
        let { jsonArray ->
            when (T::class) {
                Country::class -> Country(jsonArray.getJSONObject(it)) as T
                Global::class -> Global(jsonArray.getJSONObject(it)) as T
                else -> throw JSONException(R.string.error_parse_json.toString())
            }
        }
    }
}
