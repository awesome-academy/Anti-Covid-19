package com.sunasterisk.anticovid_19.data.model

import android.content.ContentValues
import android.database.Cursor

data class Information(
    val id: Int?,
    val newConfirmed: Int,
    val totalConfirmed: Int,
    val newDeaths: Int,
    val totalDeaths: Int,
    val newRecovered: Int,
    val totalRecovered: Int,
    val timeUpdate: String
) {
    constructor(cursor: Cursor) : this(
        id = cursor.getInt(cursor.getColumnIndex(ID)),
        newConfirmed = cursor.getInt(cursor.getColumnIndex(NEW_CONFIRMED)),
        totalConfirmed = cursor.getInt(cursor.getColumnIndex(TOTAL_CONFIRMED)),
        newDeaths = cursor.getInt(cursor.getColumnIndex(NEW_DEATHS)),
        totalDeaths = cursor.getInt(cursor.getColumnIndex(TOTAL_DEATHS)),
        newRecovered = cursor.getInt(cursor.getColumnIndex(NEW_RECOVERED)),
        totalRecovered = cursor.getInt(cursor.getColumnIndex(TOTAL_RECOVERED)),
        timeUpdate = cursor.getString(cursor.getColumnIndex(TIME_UPDATE))
    )

    fun getContentValues() = ContentValues().apply {
        put(ID, id)
        put(NEW_CONFIRMED, newConfirmed)
        put(TOTAL_CONFIRMED, totalConfirmed)
        put(NEW_DEATHS, newDeaths)
        put(TOTAL_DEATHS, totalDeaths)
        put(NEW_RECOVERED, newRecovered)
        put(TOTAL_RECOVERED, totalRecovered)
        put(TIME_UPDATE, timeUpdate)
    }

    companion object {
        const val TABLE_NAME = "tb_information"
        const val ID = "id"
        const val NEW_CONFIRMED = "new_confirmed"
        const val TOTAL_CONFIRMED = "total_confirmed"
        const val NEW_DEATHS = "new_deaths"
        const val TOTAL_DEATHS = "total_deaths"
        const val NEW_RECOVERED = "new_recovered"
        const val TOTAL_RECOVERED = "total_recovered"
        const val TIME_UPDATE = "time_update"
    }
}
