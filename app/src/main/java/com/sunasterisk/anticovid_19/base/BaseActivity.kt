package com.sunasterisk.anticovid_19.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sunasterisk.anticovid_19.R

abstract class BaseActivity : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutResource: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
        initComponents()
    }

    protected abstract fun initComponents()

    protected fun openFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
}
