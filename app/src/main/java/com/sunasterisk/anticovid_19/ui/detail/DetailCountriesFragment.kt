package com.sunasterisk.anticovid_19.ui.detail

import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.base.BaseFragment
import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.ui.dialog.LoadingDialog
import com.sunasterisk.anticovid_19.ui.statistic.StatisticsFragment
import com.sunasterisk.anticovid_19.utils.NameConst.VIET_NAM
import com.sunasterisk.anticovid_19.utils.RepositoryUtil
import com.sunasterisk.anticovid_19.utils.showToast
import kotlinx.android.synthetic.main.fragment_detail_countries.*

class DetailCountriesFragment :
    BaseFragment(),
    DetailCountriesContract.View,
    TextView.OnEditorActionListener
{

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
        buttonSearch.setOnClickListener { searchCountries() }
        editTextSearch.setOnEditorActionListener(this)
    }

    override fun showCountries(countries: List<Country>) {
        adapter.updateCountries(countries)
        recyclerViewCountries.scrollToPosition(0)
    }

    override fun showError(error: String) {
        context?.showToast(error)
    }

    override fun showMessage(message: String) {
        context?.showToast(message)
    }

    override fun showLoading() {
        myDialog?.show()
    }

    override fun hideLoading() {
        myDialog?.hide()
    }

    override fun onEditorAction(textView: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) searchCountries()
        return true
    }

    private fun initAdapter() {
        recyclerViewCountries.adapter = adapter.apply {
            onItemClick = { item, position ->
                showStatisticsFragment(item, item.country == VIET_NAM)
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

    private fun showStatisticsFragment(country: Country, isVietNamCountry: Boolean) {
        activity?.apply {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    StatisticsFragment.newInstance(country, isVietNamCountry)
                )
                .commit()
        }
    }

    private fun searchCountries() {
        val value = editTextSearch.text.toString()
        presenter?.searchCountries(value)
        hideKeyBroad()
    }

    private fun hideKeyBroad() {
        val view = activity?.currentFocus
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    companion object {
        fun getInstance() = DetailCountriesFragment()
    }
}
