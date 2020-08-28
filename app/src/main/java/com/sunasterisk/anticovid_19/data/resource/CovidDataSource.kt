package com.sunasterisk.anticovid_19.data.resource

import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.resource.remote.util.OnDataLoadCallBack

interface CovidDataSource {
    interface Local {

    }

    interface Remote {
        fun getCountryInformation(callback: OnDataLoadCallBack<List<Country>>)
    }
}
