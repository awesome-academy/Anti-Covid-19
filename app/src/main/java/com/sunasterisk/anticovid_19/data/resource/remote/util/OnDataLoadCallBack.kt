package com.sunasterisk.anticovid_19.data.resource.remote.util

import java.lang.Exception

interface OnDataLoadCallBack<T> {
    fun onSuccess(data: T)
    fun onFail(exception: Exception)
}
