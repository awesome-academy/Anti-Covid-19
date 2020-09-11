package com.sunasterisk.anticovid_19.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.model.Information
import com.sunasterisk.anticovid_19.data.resource.remote.util.OnDataLoadCallback
import com.sunasterisk.anticovid_19.ui.main.MainActivity
import com.sunasterisk.anticovid_19.utils.NameConst.VIET_NAM
import com.sunasterisk.anticovid_19.utils.RepositoryUtil
import com.sunasterisk.anticovid_19.utils.showToast
import java.lang.Exception

class CovidInformationService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        val repository = RepositoryUtil.getRepository(this)
        repository.getCountryInformation(object : OnDataLoadCallback<List<Country>> {
            override fun onSuccess(data: List<Country>) {
                data.firstOrNull {
                    it.country == VIET_NAM
                }?.let { checkUpdate(it) }
            }

            override fun onFail(exception: Exception) {
                this@CovidInformationService.showToast(exception.message.toString())
            }
        })
    }

    private fun checkUpdate(country: Country) {
        val repository = RepositoryUtil.getRepository(this)
        var information = repository.getLastInformation()
        if (country.totalConfirmed != information?.totalConfirmed ||
            country.totalDeaths != information.totalDeaths ||
            country.totalRecovered != information.totalRecovered
        ) {
            information = Information(
                information?.id,
                country.newConfirmed,
                country.totalConfirmed,
                country.newDeaths,
                country.totalDeaths,
                country.newRecovered,
                country.totalRecovered,
                country.date
            )
            repository.updateInformation(information)
            showNotification(country)
        }
    }

    private fun showNotification(country: Country) {
        val intent = MainActivity.getIntentFromNotification(this)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.title_notification))
            .setContentText(
                getString(R.string.text_head_notification, country.newConfirmed) +
                        getString(R.string.text_body_notification, country.newDeaths) +
                        getString(R.string.text_end_notification, country.newRecovered)
            )
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder)
        }
    }

    companion object {
        private const val JOB_ID = 98
        private const val NOTIFICATION_ID = 99
        private const val CHANNEL_ID = "1"
        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, CovidInformationService::class.java, JOB_ID, work)
        }
    }
}
