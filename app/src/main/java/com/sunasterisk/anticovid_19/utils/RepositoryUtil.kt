package com.sunasterisk.anticovid_19.utils

import android.content.Context
import com.sunasterisk.anticovid_19.data.resource.CovidRepository
import com.sunasterisk.anticovid_19.data.resource.local.CovidLocalDataSource
import com.sunasterisk.anticovid_19.data.resource.local.dao.DocumentDaoImpl
import com.sunasterisk.anticovid_19.data.resource.local.dao.InformationDaoImpl
import com.sunasterisk.anticovid_19.data.resource.local.db.MyDatabase
import com.sunasterisk.anticovid_19.data.resource.remote.CovidRemoteDataSource

object RepositoryUtil {
    fun getRepository(context: Context): CovidRepository {
        val myDatabase = MyDatabase.getInstance(context)
        val preferences = SharedPreferencesHelper.getInstance(context)
        val local =
            CovidLocalDataSource.getInstance(
                InformationDaoImpl.getInstance(myDatabase),
                DocumentDaoImpl(),
                preferences
            )
        val remote = CovidRemoteDataSource()
        return CovidRepository.getInstance(remote, local)
    }
}
