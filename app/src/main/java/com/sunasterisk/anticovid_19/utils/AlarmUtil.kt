package com.sunasterisk.anticovid_19.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import com.sunasterisk.anticovid_19.broadcast.MyBroadcast

object AlarmUtil {
    fun create(context: Context) {
        val intent = Intent(context, MyBroadcast::class.java)
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager?.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR,
            AlarmManager.INTERVAL_HALF_HOUR,
            pendingIntent
        )
    }

    fun cancel(context: Context) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context, MyBroadcast::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager?.cancel(pendingIntent)
    }
}
