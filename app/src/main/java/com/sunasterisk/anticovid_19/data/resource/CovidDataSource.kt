package com.sunasterisk.anticovid_19.data.resource

import com.sunasterisk.anticovid_19.data.model.*
import com.sunasterisk.anticovid_19.data.resource.remote.util.OnDataLoadCallback

interface CovidDataSource {
    interface Local {
        fun getLastInformation(): Information?
        fun addInformation(information: Information)
        fun updateInformation(information: Information)
        fun getNotification(): Boolean
        fun updateNotification(isAllowNotification: Boolean)
        fun getSymptoms(): List<Document>
        fun getPrevents(): List<Document>
        fun getLanguage(): Boolean
        fun updateLanguage(isVietnamese: Boolean)
    }

    interface Remote {
        fun getCountryInformation(callback: OnDataLoadCallback<List<Country>>)
        fun getGlobalInformation(callback: OnDataLoadCallback<Global>)
        fun getNews(callback: OnDataLoadCallback<List<News>>)
    }
}
