package com.sunasterisk.anticovid_19.data.resource

import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.model.Global
import com.sunasterisk.anticovid_19.data.model.Information
import com.sunasterisk.anticovid_19.data.model.News
import com.sunasterisk.anticovid_19.data.resource.remote.util.OnDataLoadCallback

interface CovidDataSource {
    interface Local {
        fun getLastInformation(): Information?
        fun addInformation(information: Information)
        fun updateInformation(information: Information)
        fun getNotification(): Boolean
        fun updateNotification(isAllowNotification: Boolean)
    }

    interface Remote {
        fun getCountryInformation(callback: OnDataLoadCallback<List<Country>>)
        fun getGlobalInformation(callback: OnDataLoadCallback<Global>)
        fun getNews(callback: OnDataLoadCallback<List<News>>)
    }
}
