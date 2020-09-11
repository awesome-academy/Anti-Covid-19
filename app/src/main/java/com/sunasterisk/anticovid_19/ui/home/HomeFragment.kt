package com.sunasterisk.anticovid_19.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.RadioGroup
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.base.BaseFragment
import com.sunasterisk.anticovid_19.data.model.Document
import com.sunasterisk.anticovid_19.ui.detail.DetailCountriesFragment
import com.sunasterisk.anticovid_19.utils.FragmentConst
import com.sunasterisk.anticovid_19.utils.RepositoryUtil
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment :
    BaseFragment(),
    HomeContract.View,
    RadioGroup.OnCheckedChangeListener
{
    private var presenter: HomePresenter? = null
    private val adapterSymptoms = HomeAdapter()
    private val adapterPrevention = HomeAdapter()

    override val layoutResource: Int
        get() = R.layout.fragment_home

    override fun initData() {
        initAdapter()
        initPresenter()
        presenter?.start()
    }

    override fun initActions() {
        radioGroupChangeLanguage.setOnCheckedChangeListener(this)
        buttonHotline.setOnClickListener { callPhone() }
    }

    override fun showDocuments(symptoms: List<Document>, prevents: List<Document>) {
        adapterSymptoms.update(symptoms)
        adapterPrevention.update(prevents)
    }

    override fun showLanguage(isVietnamese: Boolean) {
        if (isVietnamese) {
            radioButtonVietnamese.isChecked = true
        } else {
            radioButtonEnglish.isChecked = true
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.radioButtonVietnamese -> presenter?.updateLanguage(true)
            R.id.radioButtonEnglish -> presenter?.updateLanguage(false)
        }
    }

    private fun initAdapter() {
        recyclerViewSymptoms.adapter = adapterSymptoms
        recyclerViewPrevention.adapter = adapterPrevention
    }

    private fun initPresenter() {
        val context = context ?: return
        val repository = RepositoryUtil.getRepository(context)
        presenter = HomePresenter(this, repository)
    }

    private fun callPhone() {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(HOT_LINE)))
    }

    companion object {
        private const val HOT_LINE = "tel: 19009095"
        const val ACTION_HOME_FRAGMENT = "ACTION_HOME_FRAGMENT"
        fun newInstance(isRootFragment: Boolean) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(FragmentConst.BUNDLE_IS_ROOT_FRAGMENT, isRootFragment)
                }
            }
    }
}
