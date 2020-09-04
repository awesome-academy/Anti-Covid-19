package com.sunasterisk.anticovid_19.ui.detail

import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.base.BaseFragment
import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.ui.dialog.LoadingDialog
import com.sunasterisk.anticovid_19.utils.RepositoryUtil
import com.sunasterisk.anticovid_19.utils.showToast
import kotlinx.android.synthetic.main.fragment_detail_countries.*

class DetailCountriesFragment : BaseFragment(), DetailCountriesContract.View {

    private var presenter: DetailCountriesPresenter? = null
    private val adapter = DetailCountriesAdapter()
    private var myDialog: LoadingDialog? = null
    
    override val layoutResource: Int
        get() = R.layout.fragment_detail_countries

    override fun initData() {
        initAdapter()
        initPresenter()
        initDialog()
        presenter?.start()
    }

    override fun initActions() {
    }

    override fun showCountries(countries: List<Country>) {
        adapter.updateCountries(countries)
    }

    override fun showError(error: String) {
        context?.showToast(error)
    }

    override fun showLoading() {
        myDialog?.show()
    }

    override fun hideLoading() {
        myDialog?.hide()
    }

    private fun initAdapter() {
        recyclerViewCountries.adapter = adapter.apply {
            onItemClick = { item, position ->
                context?.showToast(item.country)
            }
        }
    }
    
    private fun initPresenter() {
        val context = context ?: return
        val repository = RepositoryUtil.getRepository(context)
        presenter = DetailCountriesPresenter(this, repository)
    }

    private fun initDialog() {
        context?.let { myDialog = LoadingDialog(it) }
    }

    companion object {
        fun getInstance() = DetailCountriesFragment()
    }
}
