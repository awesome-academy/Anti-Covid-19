package com.sunasterisk.anticovid_19.data.resource

import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.model.Information
import com.sunasterisk.anticovid_19.data.resource.remote.util.OnDataLoadCallBack

class CovidRepository private constructor(
    private val remote: CovidDataSource.Remote,
    private val local: CovidDataSource.Local
): CovidDataSource.Local, CovidDataSource.Remote {

    override fun getCountryInformation(callback: OnDataLoadCallBack<List<Country>>) {
        remote.getCountryInformation(callback)
    }

    override fun getLastInformation(): Information? = local.getLastInformation()

    override fun addInformation(information: Information) {
        local.addInformation(information)
    }

    override fun updateInformation(information: Information) {
        local.updateInformation(information)
    }

    companion object {
        private var instance: CovidRepository? = null
        fun getInstance(remote: CovidDataSource.Remote, local: CovidDataSource.Local) =
            instance ?: CovidRepository(remote, local).also { instance = it }
    }
}
