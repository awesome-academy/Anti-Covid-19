package com.sunasterisk.anticovid_19.ui.detail

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.base.BaseFragment
import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.ui.dialog.LoadingDialog
import com.sunasterisk.anticovid_19.ui.statistic.StatisticsFragment
import com.sunasterisk.anticovid_19.utils.*
import com.sunasterisk.anticovid_19.utils.ModelConst.TOTAL_CONFIRMED
import com.sunasterisk.anticovid_19.utils.ModelConst.TOTAL_DEATHS
import com.sunasterisk.anticovid_19.utils.ModelConst.TOTAL_RECOVERED
import com.sunasterisk.anticovid_19.utils.NameConst.VIET_NAM
import kotlinx.android.synthetic.main.fragment_detail_countries.*

class DetailCountriesFragment :
    BaseFragment(),
    DetailCountriesContract.View,
    TextView.OnEditorActionListener,
    View.OnClickListener
{

    private var presenter: DetailCountriesPresenter? = null
    private val adapter = DetailCountriesAdapter()
    private var myDialog: LoadingDialog? = null
    private var isConnection = false
    private var stateButton = 0

    override val layoutResource: Int
        get() = R.layout.fragment_detail_countries

    override fun initData() {
        initAdapter()
        initPresenter()
        initDialog()
        if (!isConnection) {
            view?.make(getString(R.string.msg_connection_fail))
            return
        }
        presenter?.start()
    }

    override fun initActions() {
        buttonSearch.setOnClickListener(this)
        editTextSearch.setOnEditorActionListener(this)
        textViewInfected.setOnClickListener(this)
        textViewDeath.setOnClickListener(this)
        textViewRecovered.setOnClickListener(this)
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
        myDialog?.dismiss()
    }

    override fun onEditorAction(textView: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) searchCountries()
        return true
    }

    override fun onClick(view: View) {
        if (isConnection) {
            when (view.id) {
                R.id.buttonSearch -> searchCountries()
                R.id.textViewInfected -> sortCountries(TOTAL_CONFIRMED)
                R.id.textViewDeath -> sortCountries(TOTAL_DEATHS)
                R.id.textViewRecovered -> sortCountries(TOTAL_RECOVERED)
            }
        } else {
            view.make(getString(R.string.msg_connection_fail))
            hideKeyBroad()
        }
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
        isConnection = NetworkUtil.isConnection(context)
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
        editTextSearch.text.clear()
    }

    private fun hideKeyBroad() {
        val view = activity?.currentFocus
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun hintIcon() {
        textViewInfected.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        textViewDeath.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        textViewRecovered.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    }

    private fun updateIcon() {
        when (stateButton) {
            STATE_INFECTED_ASC ->
                textViewInfected.setLeftDrawable(R.drawable.ic_baseline_arrow_downward_24)
            STATE_INFECTED_DESC ->
                textViewInfected.setLeftDrawable(R.drawable.ic_baseline_arrow_upward_24)
            STATE_DEATH_ASC ->
                textViewDeath.setLeftDrawable(R.drawable.ic_baseline_arrow_downward_24)
            STATE_DEATH_DESC ->
                textViewDeath.setLeftDrawable(R.drawable.ic_baseline_arrow_upward_24)
            STATE_RECOVERED_ASC ->
                textViewRecovered.setLeftDrawable(R.drawable.ic_baseline_arrow_downward_24)
            STATE_RECOVERED_DESC ->
                textViewRecovered.setLeftDrawable(R.drawable.ic_baseline_arrow_upward_24)
        }
    }

    private fun sortCountries(key: String) {
        hintIcon()
        when (key) {
            TOTAL_CONFIRMED -> {
                stateButton = if (stateButton == STATE_INFECTED_ASC) {
                    presenter?.sortCountries(key, false)
                    STATE_INFECTED_DESC
                } else {
                    presenter?.sortCountries(key, true)
                    STATE_INFECTED_ASC
                }
            }
            TOTAL_DEATHS -> {
                stateButton = if (stateButton == STATE_DEATH_ASC) {
                    presenter?.sortCountries(key, false)
                    STATE_DEATH_DESC
                } else {
                    presenter?.sortCountries(key, true)
                    STATE_DEATH_ASC
                }
            }
            TOTAL_RECOVERED -> {
                stateButton = if (stateButton == STATE_RECOVERED_ASC) {
                    presenter?.sortCountries(key, false)
                    STATE_RECOVERED_DESC
                } else {
                    presenter?.sortCountries(key, true)
                    STATE_RECOVERED_ASC
                }
            }
        }
        updateIcon()
    }

    companion object {
        private const val STATE_INFECTED_ASC = 1
        private const val STATE_INFECTED_DESC = 2
        private const val STATE_DEATH_ASC = 3
        private const val STATE_DEATH_DESC = 4
        private const val STATE_RECOVERED_ASC = 5
        private const val STATE_RECOVERED_DESC = 6
        fun getInstance() = DetailCountriesFragment()
    }
}
