package com.sunasterisk.anticovid_19.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.base.BaseActivity
import com.sunasterisk.anticovid_19.base.BaseFragment
import com.sunasterisk.anticovid_19.ui.detail.DetailCountriesFragment
import com.sunasterisk.anticovid_19.ui.home.HomeFragment
import com.sunasterisk.anticovid_19.ui.news.NewsFragment
import com.sunasterisk.anticovid_19.ui.statistic.StatisticsFragment
import com.sunasterisk.anticovid_19.utils.FragmentConst.BUNDLE_ACTION
import com.sunasterisk.anticovid_19.utils.FragmentConst.BUNDLE_COUNTRY
import com.sunasterisk.anticovid_19.utils.FragmentConst.BUNDLE_IS_ROOT_FRAGMENT
import com.sunasterisk.anticovid_19.utils.FragmentConst.BUNDLE_IS_VIETNAM
import com.sunasterisk.anticovid_19.utils.FragmentConst.BUNDLE_NAME_TAB
import com.sunasterisk.anticovid_19.utils.FragmentConst.BUNDLE_SHOULD_ADD
import com.sunasterisk.anticovid_19.utils.FragmentConst.TAB_HOME
import com.sunasterisk.anticovid_19.utils.FragmentConst.TAB_NEWS
import com.sunasterisk.anticovid_19.utils.FragmentConst.TAB_STATISTICS
import com.sunasterisk.anticovid_19.utils.FragmentUtil
import com.sunasterisk.anticovid_19.utils.StackListManager.updateStackIndex
import com.sunasterisk.anticovid_19.utils.StackListManager.updateStackToIndexFirst
import com.sunasterisk.anticovid_19.utils.StackListManager.updateTabStackIndex
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity(), BaseFragment.FragmentInteractionCallback {

    private var tagStacks = mutableMapOf<String, Stack<String>>()
    private var menuStacks = mutableListOf<String>()
    private var stackList = mutableListOf<String>()
    private var currentTab = ""
    private var currentFragment = Fragment()
    private var homeFragment = HomeFragment.newInstance(true)
    private var newsFragment = NewsFragment.newInstance(true)
    private var statisticsFragment =
        StatisticsFragment.newInstance(
            null,
            isVietNamCountry = true,
            isRootFragment = true
        )

    override val layoutResource: Int get() = R.layout.activity_main

    override fun initComponents() {
        createStack()
        bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavigationItemSelected)
        if (intent.getBooleanExtra(EXTRA_OPEN_FROM_NOTIFICATION, false)) {
            bottomNavigationView.selectedItemId = R.id.menuStatistics
            return
        }
        bottomNavigationView.selectedItemId = R.id.menuHome
        bottomNavigationView.setOnNavigationItemReselectedListener(onBottomNavigationItemReselected)
    }

    override fun onFragmentInteractionCallBack(bundle: Bundle) {
        when (bundle.getString(BUNDLE_ACTION)) {
            HomeFragment.ACTION_HOME_FRAGMENT -> showFragment(
                bundle,
                HomeFragment.newInstance(false)
            )
            StatisticsFragment.ACTION_STATISTICS_FRAGMENT -> showFragment(
                bundle,
                StatisticsFragment.newInstance(
                    bundle.getParcelable(BUNDLE_COUNTRY),
                    bundle.getBoolean(BUNDLE_IS_VIETNAM),
                    false
                )
            )
            NewsFragment.ACTION_NEWS_FRAGMENT -> showFragment(
                bundle,
                NewsFragment.newInstance(false)
            )
            DetailCountriesFragment.ACTION_DETAIL_FRAGMENT -> showFragment(
                bundle,
                DetailCountriesFragment.newInstance(false)
            )
        }
    }

    override fun onBackPressed() {
        resolveBackPressed()
    }

    private val onBottomNavigationItemReselected =
        BottomNavigationView.OnNavigationItemReselectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuHome -> popStackExceptFirst()
                R.id.menuStatistics -> popStackExceptFirst()
                R.id.menuNews -> popStackExceptFirst()
            }
        }

    private val onBottomNavigationItemSelected =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuHome -> selectedTab(TAB_HOME)
                R.id.menuStatistics -> selectedTab(TAB_STATISTICS)
                R.id.menuNews -> selectedTab(TAB_NEWS)
            }
            true
        }

    private fun createStack() {
        menuStacks.add(TAB_HOME)

        stackList.add(TAB_HOME)
        stackList.add(TAB_STATISTICS)
        stackList.add(TAB_NEWS)

        tagStacks[TAB_HOME] = Stack()
        tagStacks[TAB_STATISTICS] = Stack()
        tagStacks[TAB_NEWS] = Stack()
    }

    private fun selectedTab(tabId: String) {
        currentTab = tabId
        BaseFragment.setCurrentTab(currentTab)
        if (tagStacks[tabId]?.size == 0) {
            when (tabId) {
                TAB_HOME -> {
                    FragmentUtil.addInitialTabFragment(
                        supportFragmentManager,
                        tagStacks,
                        TAB_HOME,
                        homeFragment,
                        R.id.fragmentContainer,
                        true
                    )
                    resolveStackLists(tabId)
                    assignCurrentFragment(homeFragment)
                }
                TAB_STATISTICS -> {
                    FragmentUtil.addAdditionalTabFragment(
                        supportFragmentManager,
                        tagStacks,
                        TAB_STATISTICS,
                        statisticsFragment,
                        currentFragment,
                        R.id.fragmentContainer,
                        true
                    )
                    resolveStackLists(tabId)
                    assignCurrentFragment(statisticsFragment)
                }
                TAB_NEWS -> {
                    FragmentUtil.addAdditionalTabFragment(
                        supportFragmentManager,
                        tagStacks,
                        TAB_NEWS,
                        newsFragment,
                        currentFragment,
                        R.id.fragmentContainer,
                        true
                    )
                    resolveStackLists(tabId)
                    assignCurrentFragment(newsFragment)
                }
            }
        } else {
            val targetFragment =
                supportFragmentManager.findFragmentByTag(tagStacks[tabId]?.lastElement())
            targetFragment?.let {
                FragmentUtil.showHideTabFragment(
                    supportFragmentManager,
                    targetFragment,
                    currentFragment
                )
                resolveStackLists(tabId)
                assignCurrentFragment(targetFragment)
            }
        }
    }

    private fun resolveBackPressed() {
        var stackValue = 0
        if (tagStacks[currentTab]?.size == 1) {
            val value = tagStacks[stackList[1]]
            value?.let {
                if (value.size > 1) {
                    stackValue = value.size
                    popAndNavigateToPreviousMenu()
                }
            }
            if (stackValue <= 1) {
                if (menuStacks.size > 1) {
                    navigateToPreviousMenu()
                } else {
                    finish()
                }
            }
        } else {
            popFragment()
        }
    }

    private fun popAndNavigateToPreviousMenu() {
        val tempCurrent = stackList[0]
        currentTab = stackList[1]
        BaseFragment.setCurrentTab(currentTab)
        bottomNavigationView.selectedItemId = resolveTabPositions(currentTab)
        val targetFragment =
            supportFragmentManager.findFragmentByTag(tagStacks[currentTab]?.lastElement())
        targetFragment?.let {
            FragmentUtil.showHideTabFragment(
                supportFragmentManager,
                targetFragment,
                currentFragment
            )
            assignCurrentFragment(targetFragment)
        }
        updateStackToIndexFirst(stackList, tempCurrent)
        menuStacks.removeAt(0)
    }

    private fun navigateToPreviousMenu() {
        menuStacks.removeAt(0)
        currentTab = menuStacks[0]
        BaseFragment.setCurrentTab(currentTab)
        bottomNavigationView.selectedItemId = resolveTabPositions(currentTab)
        val targetFragment =
            supportFragmentManager.findFragmentByTag(tagStacks[currentTab]?.lastElement())
        targetFragment?.let {
            FragmentUtil.showHideTabFragment(
                supportFragmentManager,
                targetFragment,
                currentFragment
            )
            assignCurrentFragment(targetFragment)
        }
    }

    private fun popFragment() {
        val fragmentTag =
            tagStacks[currentTab]?.elementAt(tagStacks[currentTab]!!.size - 2)
        val fragment =
            supportFragmentManager.findFragmentByTag(fragmentTag)
        tagStacks[currentTab]?.pop()
        fragment?.let {
            FragmentUtil.removeFragment(supportFragmentManager, fragment, currentFragment)
            assignCurrentFragment(fragment)
        }
    }

    private fun popStackExceptFirst() {
        if (tagStacks[currentTab]?.size == 1) {
            return
        }
        while (!tagStacks[currentTab].isNullOrEmpty()
            && !supportFragmentManager.findFragmentByTag(
                tagStacks[currentTab]?.peek()
            )?.arguments?.getBoolean(BUNDLE_IS_ROOT_FRAGMENT)!!
        ) {
            supportFragmentManager.findFragmentByTag(tagStacks[currentTab]?.peek())?.let {
                supportFragmentManager.beginTransaction().remove(it)
            }
            tagStacks[currentTab]?.pop()
        }
        val fragment =
            supportFragmentManager.findFragmentByTag(tagStacks[currentTab]?.elementAt(0))
        fragment?.let {
            FragmentUtil.removeFragment(supportFragmentManager, fragment, currentFragment)
            assignCurrentFragment(fragment)
        }
    }

    private fun showFragment(bundle: Bundle, fragmentToAdd: Fragment) {
        val tab = bundle.getString(BUNDLE_NAME_TAB).toString()
        val shouldAdd = bundle.getBoolean(BUNDLE_SHOULD_ADD)
        FragmentUtil.addShowHideFragment(
            supportFragmentManager,
            tagStacks,
            tab,
            fragmentToAdd,
            getCurrentFragmentFromShownStack(),
            R.id.fragmentContainer,
            shouldAdd
        )
        assignCurrentFragment(fragmentToAdd)
    }

    private fun resolveTabPositions(currentTab: String): Int = when (currentTab) {
        TAB_HOME -> R.id.menuHome
        TAB_STATISTICS -> R.id.menuStatistics
        TAB_NEWS -> R.id.menuNews
        else -> 0
    }

    private fun resolveStackLists(tabId: String) {
        updateStackIndex(stackList, tabId)
        updateTabStackIndex(menuStacks, tabId)
    }

    private fun getCurrentFragmentFromShownStack(): Fragment {
        val fragment = supportFragmentManager.findFragmentByTag(
            tagStacks[currentTab]?.elementAt(tagStacks[currentTab]!!.size - 1)
        )
        return fragment ?: Fragment()
    }

    private fun assignCurrentFragment(current: Fragment) {
        currentFragment = current
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
