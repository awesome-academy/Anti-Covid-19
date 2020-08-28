package com.sunasterisk.anticovid_19.data.resource.remote

import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.resource.CovidDataSource
import com.sunasterisk.anticovid_19.data.resource.remote.util.OnDataLoadCallBack
import com.sunasterisk.anticovid_19.data.resource.remote.util.RemoteAsyncTask
import com.sunasterisk.anticovid_19.utils.LinkConst.COVID_API
import com.sunasterisk.anticovid_19.utils.NameConst.COUNTRIES
import com.sunasterisk.anticovid_19.utils.parseJsonArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CovidRemoteDataSource : CovidDataSource.Remote {

    override fun getCountryInformation(callback: OnDataLoadCallBack<List<Country>>) {
        RemoteAsyncTask(callback) {
            getDataApi()
        }.execute(COVID_API)
    }

    private fun getDataApi(): List<Country> {
        val content = StringBuffer()

        val url = URL(COVID_API)
        val urlOpenConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val inputStreamReader = InputStreamReader(urlOpenConnection.inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)

        bufferedReader.forEachLine {
            content.append(it)
        }

        return JSONObject(content.toString()).getString(COUNTRIES).parseJsonArray()
    }
}
