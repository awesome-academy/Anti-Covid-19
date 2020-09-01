package com.sunasterisk.anticovid_19.ui.statistic

import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.model.Global
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
        repository.getCountryInformation(object : OnDataLoadCallback<List<Country>>{
            override fun onSuccess(data: List<Country>) {
                for (i in data.indices) {
                    if (data[i].country == VIET_NAM) {
                        view.showInformationInVietNam(data[i], data[i].date)
                        break
                    }
                }
                view.hideLoading()
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.toString())
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
        view.showMessage(msgId.toString())
    }

    override fun checkNotification() {
        view.showNotification(repository.getNotification())
    }
}
