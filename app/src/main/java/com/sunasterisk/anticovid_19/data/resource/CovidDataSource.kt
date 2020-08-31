package com.sunasterisk.anticovid_19.data.resource

import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.model.Global
import com.sunasterisk.anticovid_19.data.model.Information
import com.sunasterisk.anticovid_19.data.resource.remote.util.OnDataLoadCallBack

interface CovidDataSource {
    interface Local {
        fun getLastInformation(): Information?
        fun addInformation(information: Information)
        fun updateInformation(information: Information)
    }

    interface Remote {
        fun getCountryInformation(callback: OnDataLoadCallBack<List<Country>>)
        fun getGlobalInformation(callback: OnDataLoadCallBack<Global>)
    }
}
