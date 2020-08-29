package com.sunasterisk.anticovid_19.utils

import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.model.Global
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

@Throws(JSONException::class)
inline fun <reified T> String.parseJsonArray() = JSONArray(this).let { jsonArray ->
    List(jsonArray.length()) { index ->
        when(T::class) {
            Country::class -> Country(jsonArray.getJSONObject(index)) as T
            Global::class -> Global(jsonArray.getJSONObject(index)) as T
            else -> throw JSONException(R.string.error_parse_json.toString())
        }
    }
}

@Throws(JSONException::class)
inline fun <reified T> String.parseJsonObject() = JSONObject(this).let { jsonObject ->
    when (T::class) {
        Country::class -> Country(jsonObject) as T
        Global::class -> Global(jsonObject) as T
        else -> throw JSONException(R.string.error_parse_json.toString())
    }
}
