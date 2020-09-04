package com.sunasterisk.anticovid_19.ui.statistic

import android.view.View
import android.widget.RadioGroup
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.base.BaseFragment
import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.model.Global
import com.sunasterisk.anticovid_19.ui.detail.DetailCountriesFragment
import com.sunasterisk.anticovid_19.ui.dialog.LoadingDialog
import com.sunasterisk.anticovid_19.ui.main.MainActivity
import com.sunasterisk.anticovid_19.utils.RepositoryUtil
import com.sunasterisk.anticovid_19.utils.TimeConst.ID_TIMEZONE
import com.sunasterisk.anticovid_19.utils.TimeConst.INPUT_TIME_FORMAT
import com.sunasterisk.anticovid_19.utils.TimeConst.OUTPUT_TIME_FORMAT
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
    private var myDialog: LoadingDialog? = null

    override val layoutResource: Int
        get() = R.layout.fragment_statistics

    override fun initData() {
        initPresenter()
        initDialog()
        if (radioButtonVietnamese.isChecked) presenter?.start()
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

    override fun showInformationInVietNam(country: Country, newestTime: String) = with(country) {
        textViewTotalInfected.text = totalConfirmed.toString()
        textViewTotalDeath.text = totalDeaths.toString()
        textViewTotalRecovered.text = totalRecovered.toString()
        textViewNewInfected.text = getString(R.string.text_plus_information, newConfirmed)
        textViewNewDeath.text = getString(R.string.text_plus_information, newDeaths)
        textViewNewRecovered.text = getString(R.string.text_plus_information, newRecovered)
        textViewSeeDetail.visibility = View.INVISIBLE
        updateNewestTime(newestTime)
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
        when (checkedId) {
            R.id.radioButtonVietnamese -> presenter?.getInformationInVietnNam()
            R.id.radioButtonWorld -> presenter?.getInformationInWorld()
        }
    }

    private fun initPresenter() {
        val context = context ?: return
        val repository = RepositoryUtil.getRepository(context)
        presenter = StatisticsPresenter(this, repository)
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
        fun getInstance() = StatisticsFragment()
    }
}
