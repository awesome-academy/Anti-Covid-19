package com.sunasterisk.anticovid_19.ui.statistic

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.core.os.bundleOf
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.base.BaseFragment
import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.model.Global
import com.sunasterisk.anticovid_19.ui.detail.DetailCountriesFragment
import com.sunasterisk.anticovid_19.ui.dialog.LoadingDialog
import com.sunasterisk.anticovid_19.ui.main.MainActivity
import com.sunasterisk.anticovid_19.utils.NetworkUtil
import com.sunasterisk.anticovid_19.utils.RepositoryUtil
import com.sunasterisk.anticovid_19.utils.TimeConst.ID_TIMEZONE
import com.sunasterisk.anticovid_19.utils.TimeConst.INPUT_TIME_FORMAT
import com.sunasterisk.anticovid_19.utils.TimeConst.OUTPUT_TIME_FORMAT
import com.sunasterisk.anticovid_19.utils.make
import com.sunasterisk.anticovid_19.utils.showToast
import kotlinx.android.synthetic.main.fragment_statistics.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class StatisticsFragment : BaseFragment(),
    StatisticsContract.View,
    RadioGroup.OnCheckedChangeListener {

    private var presenter: StatisticsPresenter? = null
    private var isAllowNotification = false
    private var isVietNamCountry = true
    private var isConnection = false
    private var country: Country? = null
    private var myDialog: LoadingDialog? = null

    override val layoutResource: Int
        get() = R.layout.fragment_statistics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            country = it.getParcelable(BUNDLE_COUNTRY)
            isVietNamCountry = it.getBoolean(BUNDLE_IS_VIETNAM)
        }
    }

    override fun initData() {
        initPresenter()
        initDialog()
        if (!isConnection) {
            view?.make(getString(R.string.msg_connection_fail))
            return
        }
        if (isVietNamCountry) {
            presenter?.start()
            return
        }
        country?.let {
            presenter?.checkNotification()
            showInformationCountry(it)
            radioButtonWorld.visibility = View.GONE
            radioButtonVietnamese.text = it.country
        }
    }

    override fun initActions() {
        radioGroupToggleInformation.setOnCheckedChangeListener(this)
        imageButtonNotification.setOnClickListener { allowDisplayNotification() }
        textViewSeeDetail.setOnClickListener { showFragment() }
    }

    override fun showInformationInWord(global: Global) = with(global) {
        textViewTotalInfected.text = totalConfirmed.toString()
        textViewTotalDeath.text = totalDeaths.toString()
        textViewTotalRecovered.text = totalRecovered.toString()
        textViewNewInfected.text = getString(R.string.text_plus_information, newConfirmed)
        textViewNewDeath.text = getString(R.string.text_plus_information, newDeaths)
        textViewNewRecovered.text = getString(R.string.text_plus_information, newRecovered)
        textViewSeeDetail.visibility = View.VISIBLE
    }

    override fun showInformationCountry(country: Country) = with(country) {
        textViewTotalInfected.text = totalConfirmed.toString()
        textViewTotalDeath.text = totalDeaths.toString()
        textViewTotalRecovered.text = totalRecovered.toString()
        textViewNewInfected.text = getString(R.string.text_plus_information, newConfirmed)
        textViewNewDeath.text = getString(R.string.text_plus_information, newDeaths)
        textViewNewRecovered.text = getString(R.string.text_plus_information, newRecovered)
        textViewSeeDetail.visibility = View.INVISIBLE
        updateNewestTime(country.date)
    }

    override fun showError(error: String) {
        context?.showToast(error)
    }

    override fun showMessage(message: String) {
        context?.showToast(message)
    }

    override fun showNotification(isAllow: Boolean) {
        isAllowNotification = isAllow
        if (isAllowNotification) {
            imageButtonNotification.setBackgroundResource(R.drawable.ic_notifications_white_24dp)
        } else {
            imageButtonNotification.setBackgroundResource(R.drawable.ic_notifications_off_white_24dp)
        }
    }

    override fun showLoading() {
        myDialog?.show()
    }

    override fun hideLoading() {
        myDialog?.hide()
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        if (isConnection) {
            when (checkedId) {
                R.id.radioButtonVietnamese -> presenter?.getInformationInVietnNam()
                R.id.radioButtonWorld -> presenter?.getInformationInWorld()
            }
        } else {
            group?.make(getString(R.string.msg_connection_fail))
        }
    }

    private fun initPresenter() {
        val context = context ?: return
        val repository = RepositoryUtil.getRepository(context)
        presenter = StatisticsPresenter(this, repository)
        isConnection = NetworkUtil.isConnection(context)
    }

    private fun initDialog() {
        context?.let { myDialog = LoadingDialog(it) }
    }

    private fun updateNewestTime(time: String) {
        val input = SimpleDateFormat(INPUT_TIME_FORMAT, Locale.getDefault())
        input.timeZone = TimeZone.getTimeZone(ID_TIMEZONE)

        val output = SimpleDateFormat(OUTPUT_TIME_FORMAT, Locale.getDefault())

        var date: Date? = null
        try {
            date = input.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        input.timeZone = TimeZone.getDefault()
        if (date != null) textViewUpdateTime.text = output.format(date)
    }

    private fun allowDisplayNotification() {
        isAllowNotification = if (isAllowNotification) {
            imageButtonNotification.setBackgroundResource(R.drawable.ic_notifications_off_white_24dp)
            presenter?.updateNotification(false)
            false
        } else {
            imageButtonNotification.setBackgroundResource(R.drawable.ic_notifications_white_24dp)
            presenter?.updateNotification(true)
            true
        }
    }

    private fun showFragment() {
        (activity as MainActivity).supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, DetailCountriesFragment.getInstance())
            .commit()
    }

    companion object {
        private const val BUNDLE_COUNTRY = "BUNDLE_COUNTRY"
        private const val BUNDLE_IS_VIETNAM = "BUNDLE_IS_VIETNAM"
        fun newInstance(country: Country, isVietNamCountry: Boolean) =
            StatisticsFragment().apply {
                arguments = bundleOf(
                    BUNDLE_COUNTRY to country,
                    BUNDLE_IS_VIETNAM to isVietNamCountry
                )
            }
    }
}
