package com.sunasterisk.anticovid_19.data.resource.remote

import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.model.Global
import com.sunasterisk.anticovid_19.data.model.News
import com.sunasterisk.anticovid_19.data.resource.CovidDataSource
import com.sunasterisk.anticovid_19.data.resource.remote.util.OnDataLoadCallback
import com.sunasterisk.anticovid_19.data.resource.remote.util.RemoteAsyncTask
import com.sunasterisk.anticovid_19.data.resource.remote.util.XMLParser
import com.sunasterisk.anticovid_19.utils.LinkConst.COVID_API
import com.sunasterisk.anticovid_19.utils.LinkConst.VNEXPRESS_RSS
import com.sunasterisk.anticovid_19.utils.ModelConst.LINK_NEWS
import com.sunasterisk.anticovid_19.utils.ModelConst.TIME_NEWS
import com.sunasterisk.anticovid_19.utils.ModelConst.TITLE_NEWS
import com.sunasterisk.anticovid_19.utils.NameConst.COUNTRIES
import com.sunasterisk.anticovid_19.utils.NameConst.COVID
import com.sunasterisk.anticovid_19.utils.NameConst.DESCRIPTION
import com.sunasterisk.anticovid_19.utils.NameConst.GLOBAL
import com.sunasterisk.anticovid_19.utils.NameConst.ITEM
import com.sunasterisk.anticovid_19.utils.parseJsonArray
import com.sunasterisk.anticovid_19.utils.parseJsonObject
import org.json.JSONObject
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Matcher
import java.util.regex.Pattern

class CovidRemoteDataSource : CovidDataSource.Remote {

    override fun getCountryInformation(callback: OnDataLoadCallback<List<Country>>) {
        RemoteAsyncTask(callback) {
            getCountryInformation()
        }.execute(COVID_API)
    }

    override fun getGlobalInformation(callback: OnDataLoadCallback<Global>) {
        RemoteAsyncTask(callback) {
            getGlobalInformation()
        }.execute(COVID_API)
    }

    override fun getNews(callback: OnDataLoadCallback<List<News>>) {
        RemoteAsyncTask(callback) {
            getNews()
        }.execute(VNEXPRESS_RSS)
    }

    private fun getCountryInformation(): List<Country> =
        JSONObject(readContentApi(COVID_API)).getString(COUNTRIES).parseJsonArray()

    private fun getGlobalInformation(): Global =
        JSONObject(readContentApi(COVID_API)).getString(GLOBAL).parseJsonObject()

    private fun getNews(): List<News> {
        val newsList = mutableListOf<News>()
        val parser = XMLParser()
        val document = parser.getDocument(readContentApi(VNEXPRESS_RSS))
        val nodeList = document?.getElementsByTagName(ITEM) as NodeList
        val nodeListDescription = document.getElementsByTagName(DESCRIPTION) as NodeList
        val pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>")

        var matcher: Matcher
        var element: Element
        var title: String
        var link: String
        var time: String
        var cdata: String
        var imgUrl: String? = null

        for (i in 0 until nodeList.length) {
            element = nodeList.item(i) as Element
            cdata = nodeListDescription.item(i + 1).textContent
            matcher = pattern.matcher(cdata)
            if (matcher.find()) imgUrl = matcher.group(1)

            title = parser.getValue(element, TITLE_NEWS).toString()
            link = parser.getValue(element, LINK_NEWS).toString()
            time = parser.getValue(element, TIME_NEWS).toString()

            if (title.contains(COVID)) {
                newsList.add(News(title, link, time, imgUrl))
            }
        }
        return newsList
    }

    private fun readContentApi(link: String): String {
        val content = StringBuffer()

        val url = URL(link)
        val urlOpenConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val inputStreamReader = InputStreamReader(urlOpenConnection.inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)

        bufferedReader.forEachLine {
            content.append(it)
        }

        return content.toString()
    }
}
