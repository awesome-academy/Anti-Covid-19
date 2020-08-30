package com.sunasterisk.anticovid_19.data.resource.local

import com.sunasterisk.anticovid_19.data.model.Information
import com.sunasterisk.anticovid_19.data.resource.CovidDataSource
import com.sunasterisk.anticovid_19.data.resource.local.dao.InformationDao

class CovidLocalDataSource(private val informationDao: InformationDao) : CovidDataSource.Local {

    override fun getLastInformation(): Information? = informationDao.getLastInformation()

    override fun addInformation(information: Information) {
        informationDao.addInformation(information)
    }

    override fun updateInformation(information: Information) {
        informationDao.updateInformation(information)
    }

    companion object {
        private var instance: CovidLocalDataSource? = null
        fun getInstance(informationDao: InformationDao) =
            instance ?: synchronized(this) {
                instance ?: CovidLocalDataSource(informationDao).also { instance = it }
            }
    }
}
