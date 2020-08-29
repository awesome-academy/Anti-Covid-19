package com.sunasterisk.anticovid_19.data.model

data class Information(
    val id: Int?,
    val newConfirmed: Int,
    val totalConfirmed: Int,
    val newDeaths: Int,
    val totalDeaths: Int,
    val newRecovered: Int,
    val totalRecovered: Int,
    val timeUpdate: String
)
