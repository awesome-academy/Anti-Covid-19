package com.sunasterisk.anticovid_19.ui.news

import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.base.BaseFragment
import com.sunasterisk.anticovid_19.data.model.News
import com.sunasterisk.anticovid_19.ui.dialog.LoadingDialog
import com.sunasterisk.anticovid_19.utils.RepositoryUtil
import com.sunasterisk.anticovid_19.utils.showToast
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : BaseFragment(), NewsContract.View {

    private val adapter = NewsAdapter()
    private var presenter: NewsPresenter? = null
    private var myDialog: LoadingDialog? = null

    override val layoutResource: Int
        get() = R.layout.fragment_news

    override fun initData() {
        initAdapter()
        initPresenter()
        initDialog()
        presenter?.start()
    }

    override fun initActions() {
    }

    override fun showNews(news: List<News>) {
        adapter.updateNes(news)
    }

    override fun showError(error: String) {
        context?.showToast(error)
    }

    override fun showLoading() {
        myDialog?.show()
    }

    override fun hideLoading() {
        myDialog?.dismiss()
    }

    private fun initAdapter() {
        recyclerViewNews.adapter = adapter.apply {
            onItemClick = { item, position ->
                context?.showToast(item.title)
            }
        }
    }

    private fun initPresenter() {
        val context = context ?: return
        val repository = RepositoryUtil.getRepository(context)
        presenter = NewsPresenter(this, repository)
    }

    private fun initDialog() {
        context?.let { myDialog = LoadingDialog(it) }
    }
}
