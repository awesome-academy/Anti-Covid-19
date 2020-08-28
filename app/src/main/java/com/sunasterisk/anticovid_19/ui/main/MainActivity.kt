package com.sunasterisk.anticovid_19.ui.main

import android.content.Context
import android.content.Intent
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.base.BaseActivity

class MainActivity : BaseActivity() {
    override val layoutResource: Int get() = R.layout.activity_main

    companion object {
        fun getIntent(context: Context) =
            Intent(context, MainActivity::class.java)
    }
}
