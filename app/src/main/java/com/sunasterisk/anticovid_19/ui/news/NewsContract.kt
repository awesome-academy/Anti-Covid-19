package com.sunasterisk.anticovid_19.ui.news

import com.sunasterisk.anticovid_19.base.BasePresenter
import com.sunasterisk.anticovid_19.base.BaseView
import com.sunasterisk.anticovid_19.data.model.News

interface NewsContract {
    interface View : BaseView {
        fun showNews(news: List<News>)
        fun showError(error: String)
    }

    interface Presenter: BasePresenter {
        fun getNews()
    }
}
