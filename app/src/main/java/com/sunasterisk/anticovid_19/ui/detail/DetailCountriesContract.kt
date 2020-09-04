package com.sunasterisk.anticovid_19.ui.detail

import com.sunasterisk.anticovid_19.base.BasePresenter
import com.sunasterisk.anticovid_19.base.BaseView
import com.sunasterisk.anticovid_19.data.model.Country

interface DetailCountriesContract {
    interface View : BaseView {
        fun showCountries(countries: List<Country>)
        fun showError(error: String)
    }

    interface Presenter: BasePresenter {
        fun getCountryInformation()
    }
}
