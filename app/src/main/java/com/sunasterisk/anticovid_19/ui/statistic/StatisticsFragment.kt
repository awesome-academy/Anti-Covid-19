package com.sunasterisk.anticovid_19.ui.statistic

import android.annotation.SuppressLint
import android.view.View
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.base.BaseFragment
import com.sunasterisk.anticovid_19.data.model.Country
import com.sunasterisk.anticovid_19.data.model.Global
import com.sunasterisk.anticovid_19.data.resource.CovidRepository
import com.sunasterisk.anticovid_19.data.resource.local.CovidLocalDataSource
import com.sunasterisk.anticovid_19.data.resource.local.dao.InformationDaoImpl
import com.sunasterisk.anticovid_19.data.resource.local.db.MyDatabase
import com.sunasterisk.anticovid_19.data.resource.remote.CovidRemoteDataSource
import com.sunasterisk.anticovid_19.ui.dialog.LoadingDialog
import com.sunasterisk.anticovid_19.utils.TimeConst.ID_TIMEZONE
import com.sunasterisk.anticovid_19.utils.TimeConst.INPUT_TIME_FORMAT
import com.sunasterisk.anticovid_19.utils.TimeConst.OUTPUT_TIME_FORMAT
import com.sunasterisk.anticovid_19.utils.showToast
import kotlinx.android.synthetic.main.fragment_statistics.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class StatisticsFragment : BaseFragment(), StatisticsContract.View {

    private var presenter: StatisticsPresenter? = null
    private lateinit var myDialog: LoadingDialog

    override val layoutResource: Int
        get() = R.layout.fragment_statistics

    override fun initData() {
        initPresenter()
        initDialog()
        presenter?.start()
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

    override fun showLoading() = myDialog.show()

    override fun hideLoading() = myDialog.hide()

    private fun initPresenter() {
        val context = context ?: return
        val myDatabase = MyDatabase.getInstance(context)
        val local =
            CovidLocalDataSource.getInstance(InformationDaoImpl.getInstance(myDatabase))
        val remote = CovidRemoteDataSource()
        val repository = CovidRepository.getInstance(remote, local)
        presenter = StatisticsPresenter(this, repository)
        myDialog = LoadingDialog(context)
    }

    private fun initDialog() {
        context?.let { myDialog = LoadingDialog(it) }
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateNewestTime(time: String) {
        val input = SimpleDateFormat(INPUT_TIME_FORMAT)
        input.timeZone = TimeZone.getTimeZone(ID_TIMEZONE)

        val output = SimpleDateFormat(OUTPUT_TIME_FORMAT)

        var date: Date? = null
        try {
            date = input.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        input.timeZone = TimeZone.getDefault()
        if (date != null) textViewUpdateTime.text = output.format(date)
    }

    companion object {
        fun getInstance() = StatisticsFragment()
    }
}
