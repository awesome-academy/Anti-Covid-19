package com.sunasterisk.anticovid_19.data.resource.remote.util

import android.os.AsyncTask
import com.sunasterisk.anticovid_19.R
import java.lang.Exception

class RemoteAsyncTask<T>(
    private val callBack: OnDataLoadCallback<T>,
    private val handle: (String) -> T
) : AsyncTask<String, Unit, T?>() {

    private var exception: Exception? = null

    override fun doInBackground(vararg params: String): T? =
        try {
            handle(params[0])
                ?: throw Exception(R.string.error_get_data_api.toString())
        } catch (e: Exception) {
            exception = e
            null
        }

    override fun onPostExecute(result: T?) {
        result?.let(callBack::onSuccess) ?: (callBack::onFail)
    }
}
