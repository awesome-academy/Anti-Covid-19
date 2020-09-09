package com.sunasterisk.anticovid_19.data.resource.local.dao

import com.sunasterisk.anticovid_19.data.model.Document

interface DocumentDao {
    fun getSymptoms(): List<Document>
    fun getPrevents(): List<Document>
}
