package com.sunasterisk.anticovid_19.ui.news

import com.sunasterisk.anticovid_19.data.model.News
import com.sunasterisk.anticovid_19.data.resource.CovidRepository
import com.sunasterisk.anticovid_19.data.resource.remote.util.OnDataLoadCallback
import java.lang.Exception

class NewsPresenter(
    private val view: NewsContract.View,
    private val repository: CovidRepository
) : NewsContract.Presenter {

    override fun start() {
        getNews()
    }

    override fun getNews() {
        view.showLoading()
        repository.getNews(object : OnDataLoadCallback<List<News>> {
            override fun onSuccess(data: List<News>) {
                view.showNews(data)
                view.hideLoading()
            }

            override fun onFail(exception: Exception) {
                view.showError(exception.message.toString())
                view.hideLoading()
            }
        })
    }
}
