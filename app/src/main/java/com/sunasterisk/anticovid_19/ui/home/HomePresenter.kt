package com.sunasterisk.anticovid_19.ui.home

import com.sunasterisk.anticovid_19.data.resource.CovidRepository

class HomePresenter(
    private val view: HomeContract.View,
    private val repository: CovidRepository
) : HomeContract.Presenter {

    override fun start() {
        checkLanguage()
        view.showDocuments(repository.getSymptoms(), repository.getPrevents())
    }

    override fun updateLanguage(isVietnamese: Boolean) {
        repository.updateLanguage(isVietnamese)
    }

    override fun checkLanguage() {
        view.showLanguage(repository.getLanguage())
    }
}
