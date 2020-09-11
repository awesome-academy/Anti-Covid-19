package com.sunasterisk.anticovid_19.ui.statistic

import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.model.Global
import com.sunasterisk.anticovid_19.data.model.Information
import com.sunasterisk.anticovid_19.data.resource.CovidRepository
import com.sunasterisk.anticovid_19.data.resource.remote.util.OnDataLoadCallback
import com.sunasterisk.anticovid_19.utils.NameConst.VIET_NAM
import java.lang.Exception

class StatisticsPresenter(
    private val view: StatisticsContract.View,
    private val repository: CovidRepository
) : StatisticsContract.Presenter {

    override fun start() {
        getInformationInVietnNam()
        checkNotification()
    }

    override fun getInformationInVietnNam() {
        view.showLoading()
        repository.getCountryInformation(object : OnDataLoadCallback<List<Country>> {
            override fun onSuccess(data: List<Country>) {
                data.firstOrNull {
                    it.country == VIET_NAM
                }?.let { country ->
                    view.showInformationCountry(country)
                    updateDatabase(country)
                }
                view.hideLoading()
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.message.toString())
                view.hideLoading()
            }
        })
    }

    override fun getInformationInWorld() {
        view.showLoading()
        repository.getGlobalInformation(object : OnDataLoadCallback<Global> {
            override fun onSuccess(data: Global) {
                view.showInformationInWord(data)
                view.hideLoading()
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.toString())
                view.hideLoading()
            }
        })
    }

    override fun updateNotification(isAllowNotification: Boolean) {
        repository.updateNotification(isAllowNotification)
        val msgId = if (isAllowNotification) {
            R.string.msg_notification_on
        } else {
            R.string.msg_notification_off
        }
        view.showMessage(msgId)
    }

    override fun checkNotification() {
        view.showNotification(repository.getNotification())
    }

    private fun updateDatabase(country: Country) {
        repository.addInformation(Information(
            null,
            country.newConfirmed,
            country.totalConfirmed,
            country.newDeaths,
            country.totalDeaths,
            country.newRecovered,
            country.totalRecovered,
            country.date
        ))
    }
}
