package com.sunasterisk.anticovid_19.ui.statistic

import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.model.Global
import com.sunasterisk.anticovid_19.data.resource.CovidRepository
import com.sunasterisk.anticovid_19.data.resource.remote.util.OnDataLoadCallBack
import com.sunasterisk.anticovid_19.utils.NameConst.VIET_NAM
import java.lang.Exception

class StatisticsPresenter(
    private val view: StatisticsContract.View,
    private val repository: CovidRepository
) : StatisticsContract.Presenter {

    override fun start() {
        getInformationInVietnNam()
    }

    override fun getInformationInVietnNam() {
        view.showLoading()
        repository.getCountryInformation(object : OnDataLoadCallBack<List<Country>>{
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
        repository.getGlobalInformation(object : OnDataLoadCallBack<Global> {
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
}
