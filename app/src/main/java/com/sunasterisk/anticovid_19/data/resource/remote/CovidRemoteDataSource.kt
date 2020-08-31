package com.sunasterisk.anticovid_19.data.resource.remote

import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.model.Global
import com.sunasterisk.anticovid_19.data.resource.CovidDataSource
import com.sunasterisk.anticovid_19.data.resource.remote.util.OnDataLoadCallBack
import com.sunasterisk.anticovid_19.data.resource.remote.util.RemoteAsyncTask
import com.sunasterisk.anticovid_19.utils.LinkConst.COVID_API
import com.sunasterisk.anticovid_19.utils.NameConst.COUNTRIES
import com.sunasterisk.anticovid_19.utils.NameConst.GLOBAL
import com.sunasterisk.anticovid_19.utils.parseJsonArray
import com.sunasterisk.anticovid_19.utils.parseJsonObject
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CovidRemoteDataSource : CovidDataSource.Remote {

    override fun getCountryInformation(callback: OnDataLoadCallBack<List<Country>>) {
        RemoteAsyncTask(callback) {
            getCountryInformation()
        }.execute(COVID_API)
    }

    override fun getGlobalInformation(callback: OnDataLoadCallBack<Global>) {
        RemoteAsyncTask(callback) {
            getGlobalInformation()
        }.execute(COVID_API)
    }

    private fun getCountryInformation(): List<Country> =
        JSONObject(readContentApi()).getString(COUNTRIES).parseJsonArray()

    private fun getGlobalInformation(): Global =
        JSONObject(readContentApi()).getString(GLOBAL).parseJsonObject()

    private fun readContentApi(): String {
        val content = StringBuffer()

        val url = URL(COVID_API)
        val urlOpenConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val inputStreamReader = InputStreamReader(urlOpenConnection.inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)

        bufferedReader.forEachLine {
            content.append(it)
        }

        return content.toString()
    }
}
