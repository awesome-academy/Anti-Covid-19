package com.sunasterisk.anticovid_19.ui.main

import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.base.BaseActivity
import com.sunasterisk.anticovid_19.ui.home.HomeFragment
import com.sunasterisk.anticovid_19.ui.news.NewsFragment
import com.sunasterisk.anticovid_19.ui.statistic.StatisticsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override val layoutResource: Int get() = R.layout.activity_main

    override fun initComponents() {
        bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavigation)
        if (intent.getBooleanExtra(EXTRA_OPEN_FROM_NOTIFICATION, false)) {
            bottomNavigationView.selectedItemId = R.id.menuStatistics
            return
        }
        bottomNavigationView.selectedItemId = R.id.menuHome
    }

    private val onBottomNavigation =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuHome -> openFragment(HomeFragment())
                R.id.menuStatistics -> openFragment(StatisticsFragment())
                R.id.menuNews -> openFragment(NewsFragment())
            }
            true
        }

    companion object {
        private const val EXTRA_OPEN_FROM_NOTIFICATION = "EXTRA_OPEN_FROM_NOTIFICATION"
        fun getIntent(context: Context) =
            Intent(context, MainActivity::class.java)

        fun getIntentFromNotification(context: Context) =
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(EXTRA_OPEN_FROM_NOTIFICATION, true)
            }
    }
}
