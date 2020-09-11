package com.sunasterisk.anticovid_19.ui.detail

import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.resource.CovidRepository
import com.sunasterisk.anticovid_19.data.resource.remote.util.OnDataLoadCallback
import com.sunasterisk.anticovid_19.utils.ModelConst.TOTAL_CONFIRMED
import com.sunasterisk.anticovid_19.utils.ModelConst.TOTAL_DEATHS
import com.sunasterisk.anticovid_19.utils.ModelConst.TOTAL_RECOVERED
import java.lang.Exception
import java.util.*

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
                view.showError(exception.message.toString())
                view.hideLoading()
            }
        })
    }

    override fun searchCountries(value: String) {
        view.showLoading()
        repository.getCountryInformation(object : OnDataLoadCallback<List<Country>> {
            override fun onSuccess(data: List<Country>) {
                val newCountries = data.filter { country ->
                    country.slug.contains(value.toLowerCase(Locale.getDefault()))
                }
                if (newCountries.isNullOrEmpty()) {
                    view.showMessage(R.string.msg_search_fail)
                } else {
                    view.showCountries(newCountries)
                }
                view.hideLoading()
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.message.toString())
                view.hideLoading()
            }
        })
    }

    override fun sortCountries(key: String, isDescending: Boolean) {
        view.showLoading()
        repository.getCountryInformation(object : OnDataLoadCallback<List<Country>> {
            override fun onSuccess(data: List<Country>) {
                if (isDescending) {
                    when (key) {
                        TOTAL_CONFIRMED -> view.showCountries(data.sortedByDescending { it.totalConfirmed })
                        TOTAL_DEATHS -> view.showCountries(data.sortedByDescending { it.totalDeaths })
                        TOTAL_RECOVERED -> view.showCountries(data.sortedByDescending { it.totalRecovered })
                    }
                } else {
                    when (key) {
                        TOTAL_CONFIRMED -> view.showCountries(data.sortedBy { it.totalConfirmed })
                        TOTAL_DEATHS -> view.showCountries(data.sortedBy { it.totalDeaths })
                        TOTAL_RECOVERED -> view.showCountries(data.sortedBy { it.totalRecovered })
                    }
                }
                view.hideLoading()
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.message.toString())
                view.hideLoading()
            }
        })
    }
}
