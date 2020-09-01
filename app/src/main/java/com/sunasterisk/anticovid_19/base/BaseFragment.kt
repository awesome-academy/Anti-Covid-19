package com.sunasterisk.anticovid_19.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    @get:LayoutRes
    protected abstract val layoutResource: Int
    protected var interactionCallback: FragmentInteractionCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(layoutResource, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentInteractionCallback) interactionCallback = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initActions()
    }

    override fun onDetach() {
        interactionCallback = null
        super.onDetach()
    }

    protected abstract fun initData()

    protected abstract fun initActions()

    interface FragmentInteractionCallback {
        fun onFragmentInteractionCallBack()
    }
}
