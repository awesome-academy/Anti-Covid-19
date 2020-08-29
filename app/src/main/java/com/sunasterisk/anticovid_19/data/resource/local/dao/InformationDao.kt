package com.sunasterisk.anticovid_19.data.resource.local.dao

import com.sunasterisk.anticovid_19.data.model.Information

interface InformationDao {
    fun getLastInformation(): Information
    fun addInformation(information: Information)
    fun updateInformation(information: Information)
}
