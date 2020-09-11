package com.sunasterisk.anticovid_19.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sunasterisk.anticovid_19.service.CovidInformationService
import com.sunasterisk.anticovid_19.utils.NetworkUtil

class MyBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, work: Intent) {
        if (NetworkUtil.isConnection(context)) CovidInformationService.enqueueWork(context, work)
    }
}
