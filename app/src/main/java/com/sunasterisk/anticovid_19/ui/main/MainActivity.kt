package com.sunasterisk.anticovid_19.ui.main

import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.base.BaseActivity
import com.sunasterisk.anticovid_19.ui.statistic.StatisticsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override val layoutResource: Int get() = R.layout.activity_main

    override fun initComponents() {
        bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavigation)
        bottomNavigationView.selectedItemId = R.id.menuStatistics
    }

    private val onBottomNavigation =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuStatistics -> openFragment(StatisticsFragment())
            }
            true
        }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, MainActivity::class.java)
    }
}
