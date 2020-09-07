package com.sunasterisk.anticovid_19.data.model

import android.os.Parcelable
import org.json.JSONObject
import com.sunasterisk.anticovid_19.utils.ModelConst.NEW_CONFIRMED
import com.sunasterisk.anticovid_19.utils.ModelConst.NEW_DEATHS
import com.sunasterisk.anticovid_19.utils.ModelConst.NEW_RECOVERED
import com.sunasterisk.anticovid_19.utils.ModelConst.TOTAL_CONFIRMED
import com.sunasterisk.anticovid_19.utils.ModelConst.TOTAL_DEATHS
import com.sunasterisk.anticovid_19.utils.ModelConst.TOTAL_RECOVERED
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
    val country: String,
    val countryCode: String,
    val slug: String,
    val newConfirmed: Int,
    val totalConfirmed: Int,
    val newDeaths: Int,
    val totalDeaths: Int,
    val newRecovered: Int,
    val totalRecovered: Int,
    val date: String,
    val premium: String
) : Parcelable {
    constructor(jsonObject: JSONObject) : this(
        jsonObject.getString(COUNTRY),
        jsonObject.getString(COUNTRY_CODE),
        jsonObject.getString(SLUG),
        jsonObject.getInt(NEW_CONFIRMED),
        jsonObject.getInt(TOTAL_CONFIRMED),
        jsonObject.getInt(NEW_DEATHS),
        jsonObject.getInt(TOTAL_DEATHS),
        jsonObject.getInt(NEW_RECOVERED),
        jsonObject.getInt(TOTAL_RECOVERED),
        jsonObject.getString(DATE),
        jsonObject.getJSONObject(PREMIUM).toString()
    )

    companion object {
        private const val COUNTRY = "Country"
        private const val COUNTRY_CODE = "CountryCode"
        private const val SLUG = "Slug"
        private const val DATE = "Date"
        private const val PREMIUM = "Premium"
    }
}
