package com.sunasterisk.anticovid_19.data.resource

import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.resource.remote.util.OnDataLoadCallBack

class CovidRepository private constructor(
    private val remote: CovidDataSource.Remote
): CovidDataSource.Local, CovidDataSource.Remote {

    override fun getCountryInformation(callback: OnDataLoadCallBack<List<Country>>) {
        remote.getCountryInformation(callback)
    }

    companion object {
        private var instance: CovidRepository? = null
        fun getInstance(remote: CovidDataSource.Remote) =
            instance ?: CovidRepository(remote).also { instance = it }
    }
}
