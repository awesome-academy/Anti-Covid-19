package com.sunasterisk.anticovid_19.ui.detail

import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.resource.CovidRepository
import com.sunasterisk.anticovid_19.data.resource.remote.util.OnDataLoadCallback
import java.lang.Exception

class DetailCountriesPresenter(
    private val view: DetailCountriesContract.View,
    private val repository: CovidRepository
) : DetailCountriesContract.Presenter {

    override fun start() {
        getCountryInformation()
    }

    override fun getCountryInformation() {
        view.showLoading()
        repository.getCountryInformation(object : OnDataLoadCallback<List<Country>> {
            override fun onSuccess(data: List<Country>) {
                view.showCountries(data)
                view.hideLoading()
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.toString())
                view.hideLoading()
            }
        })
    }
}
