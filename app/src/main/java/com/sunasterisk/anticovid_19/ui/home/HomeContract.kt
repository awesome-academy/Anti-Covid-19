package com.sunasterisk.anticovid_19.ui.home

import com.sunasterisk.anticovid_19.base.BasePresenter
import com.sunasterisk.anticovid_19.data.model.Document

interface HomeContract {
    interface View {
        fun showDocuments(symptoms: List<Document>, prevents: List<Document>)
        fun showLanguage(isVietnamese: Boolean)
    }

    interface Presenter : BasePresenter {
        fun updateLanguage(isVietnamese: Boolean)
        fun checkLanguage()
    }
}
