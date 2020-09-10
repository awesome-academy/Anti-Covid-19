package com.sunasterisk.anticovid_19.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.base.BaseFragment
import com.sunasterisk.anticovid_19.utils.FragmentConst.BUNDLE_ACTION
import com.sunasterisk.anticovid_19.utils.FragmentConst.BUNDLE_NAME_TAB
import com.sunasterisk.anticovid_19.utils.FragmentConst.BUNDLE_SHOULD_ADD
import java.util.*

object FragmentUtil {
    fun addInitialTabFragment(
        fragmentManager: FragmentManager,
        tagStacks: Map<String, Stack<String>>,
        tag: String,
        fragment: Fragment,
        layoutId: Int,
        shouldAddToStack: Boolean
    ) {
        fragmentManager.beginTransaction()
            .add(layoutId, fragment, fragment.javaClass.name + fragment.hashCode())
            .commit()
        if (shouldAddToStack) tagStacks[tag]?.push(fragment.javaClass.name + fragment.hashCode())
    }

    fun addAdditionalTabFragment(
        fragmentManager: FragmentManager,
        tagStacks: Map<String, Stack<String>>,
        tag: String,
        show: Fragment,
        hide: Fragment,
        layoutId: Int,
        shouldAddToStack: Boolean
    ) {
        fragmentManager.beginTransaction()
            .add(layoutId, show, show.javaClass.name + show.hashCode())
            .show(show)
            .hide(hide)
            .commit()
        if (shouldAddToStack) tagStacks[tag]?.push(show.javaClass.name + show.hashCode())
    }

    fun showHideTabFragment(
        fragmentManager: FragmentManager,
        show: Fragment,
        hide: Fragment
    ) {
        fragmentManager.beginTransaction()
            .hide(hide)
            .show(show)
            .commit()
    }

    fun addShowHideFragment(
        fragmentManager: FragmentManager,
        tagStacks: Map<String, Stack<String>>,
        tag: String,
        show: Fragment,
        hide: Fragment,
        layoutId: Int,
        shouldAddToStack: Boolean
    ) {
        fragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fragment_enter_from_right, R.anim.fragment_exit_to_left)
            .add(layoutId, show, show.javaClass.name + show.hashCode())
            .show(show)
            .hide(hide)
            .commit()
        if (shouldAddToStack) tagStacks[tag]?.push(show.javaClass.name + show.hashCode())
    }

    fun removeFragment(
        fragmentManager: FragmentManager,
        show: Fragment,
        remove: Fragment
    ) {
        fragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fragment_enter_from_left, R.anim.fragment_exit_to_right)
            .remove(remove)
            .show(show)
            .commit()
    }

    fun sendActionToActivity(
        action: String,
        tab: String?,
        shouldAdd: Boolean,
        fragmentInteractionCallback: BaseFragment.FragmentInteractionCallback?
    ) {
        val bundle = Bundle().apply {
            putString(BUNDLE_ACTION, action)
            putString(BUNDLE_NAME_TAB, tab)
            putBoolean(BUNDLE_SHOULD_ADD, shouldAdd)
        }
        fragmentInteractionCallback?.onFragmentInteractionCallBack(bundle)
    }
}
