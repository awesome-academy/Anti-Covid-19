package com.sunasterisk.anticovid_19.data.resource.local.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabase private constructor(
    context: Context?,
    dbName: String,
    version: Int
): SQLiteOpenHelper(context, dbName, null, version) {

    override fun onCreate(database: SQLiteDatabase?) {
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        private const val DB_NAME = "covid_vietnam.db"
        private const val DB_VERSION = 1

        private val lock = Any()
        private var instance: MyDatabase? = null
        fun getInstance(context: Context?) =
            instance ?: synchronized(lock) {
                instance ?: MyDatabase(context, DB_NAME, DB_VERSION).also { instance = it }
            }
    }
}
