package com.sunasterisk.anticovid_19.ui.statistic

import com.sunasterisk.anticovid_19.base.BasePresenter
import com.sunasterisk.anticovid_19.base.BaseView
import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.model.Global

interface StatisticsContract {
    interface View : BaseView {
        fun showInformationInWord(global: Global)
        fun showInformationInVietNam(country: Country, newestTime: String)
        fun showError(error: String)
        fun showMessage(message: String)
        fun showNotification(isAllow: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getInformationInVietnNam()
        fun getInformationInWorld()
        fun updateNotification(isAllowNotification: Boolean)
        fun checkNotification()
    }
}
