package com.sunasterisk.anticovid_19.utils

import android.widget.TextView

fun TextView.setLeftDrawable(drawable: Int) {
    setCompoundDrawablesWithIntrinsicBounds(drawable,0,0,0)
}
