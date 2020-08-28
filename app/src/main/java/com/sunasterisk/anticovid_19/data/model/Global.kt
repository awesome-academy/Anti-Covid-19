package com.sunasterisk.anticovid_19.data.model

import org.json.JSONObject
import com.sunasterisk.anticovid_19.utils.ModelConst.NEW_CONFIRMED
import com.sunasterisk.anticovid_19.utils.ModelConst.NEW_DEATHS
import com.sunasterisk.anticovid_19.utils.ModelConst.NEW_RECOVERED
import com.sunasterisk.anticovid_19.utils.ModelConst.TOTAL_CONFIRMED
import com.sunasterisk.anticovid_19.utils.ModelConst.TOTAL_DEATHS
import com.sunasterisk.anticovid_19.utils.ModelConst.TOTAL_RECOVERED

data class Global(
    val newConfirmed: Int,
    val totalConfirmed: Int,
    val newDeaths: Int,
    val totalDeaths: Int,
    val newRecovered: Int,
    val totalRecovered: Int
) {
    constructor(jsonObject: JSONObject) : this(
        jsonObject.getInt(NEW_CONFIRMED),
        jsonObject.getInt(TOTAL_CONFIRMED),
        jsonObject.getInt(NEW_DEATHS),
        jsonObject.getInt(TOTAL_DEATHS),
        jsonObject.getInt(NEW_RECOVERED),
        jsonObject.getInt(TOTAL_RECOVERED)
    )
}
