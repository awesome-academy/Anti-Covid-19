package com.sunasterisk.anticovid_19.data.resource.local

import com.sunasterisk.anticovid_19.data.model.Document
import com.sunasterisk.anticovid_19.data.model.Information
import com.sunasterisk.anticovid_19.data.resource.CovidDataSource
import com.sunasterisk.anticovid_19.data.resource.local.dao.DocumentDao
import com.sunasterisk.anticovid_19.data.resource.local.dao.InformationDao
import com.sunasterisk.anticovid_19.utils.PreferencesConst.PREFS_IS_ALLOW_NOTIFICATION
import com.sunasterisk.anticovid_19.utils.PreferencesConst.PREFS_IS_VIETNAMESE_LANGUAGE
import com.sunasterisk.anticovid_19.utils.SharedPreferencesHelper

class CovidLocalDataSource(
    private val informationDao: InformationDao,
    private val documentDao: DocumentDao,
    private val preferencesHelper: SharedPreferencesHelper
) : CovidDataSource.Local {

    override fun getLastInformation(): Information? = informationDao.getLastInformation()

    override fun addInformation(information: Information) {
        informationDao.addInformation(information)
    }

    override fun updateInformation(information: Information) {
        informationDao.updateInformation(information)
    }

    override fun getNotification(): Boolean =
        preferencesHelper[PREFS_IS_ALLOW_NOTIFICATION, Boolean::class.java]

    override fun updateNotification(isAllowNotification: Boolean) {
        preferencesHelper.put(PREFS_IS_ALLOW_NOTIFICATION, isAllowNotification)
    }

    override fun getSymptoms(): List<Document> = documentDao.getSymptoms()

    override fun getPrevents(): List<Document> = documentDao.getPrevents()

    override fun getLanguage(): Boolean =
        preferencesHelper[PREFS_IS_VIETNAMESE_LANGUAGE, Boolean::class.java]

    override fun updateLanguage(isVietnamese: Boolean) {
        preferencesHelper.put(PREFS_IS_VIETNAMESE_LANGUAGE, isVietnamese)
    }

    companion object {
        private var instance: CovidLocalDataSource? = null
        fun getInstance(
            informationDao: InformationDao,
            documentDao: DocumentDao,
            preferencesHelper: SharedPreferencesHelper
        ) =
            instance ?: synchronized(this) {
                instance ?: CovidLocalDataSource(
                    informationDao,
                    documentDao,
                    preferencesHelper
                ).also {
                    instance = it
                }
            }
    }
}
