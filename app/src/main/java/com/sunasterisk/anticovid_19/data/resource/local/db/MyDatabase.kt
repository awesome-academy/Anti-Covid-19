package com.sunasterisk.anticovid_19.data.resource.local.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sunasterisk.anticovid_19.data.model.Information

class MyDatabase private constructor(
    context: Context?,
    dbName: String,
    version: Int
): SQLiteOpenHelper(context, dbName, null, version) {

    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL(CREATE_TABLE_INFORMATION)
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        database?.execSQL(DROP_TABLE_INFORMATION)
        onCreate(database)
    }

    companion object {
        private const val DB_NAME = "covid_vietnam.db"
        private const val DB_VERSION = 1

        private const val CREATE_TABLE_INFORMATION =
            "CREATE TABLE " + Information.TABLE_NAME + "(" +
                    Information.ID + " INTEGER PRIMARY KEY, " +
                    Information.NEW_CONFIRMED + " INT, " +
                    Information.TOTAL_CONFIRMED + " INT, " +
                    Information.NEW_DEATHS + " INT, " +
                    Information.TOTAL_DEATHS + " INT, " +
                    Information.NEW_RECOVERED + " INT, " +
                    Information.TOTAL_RECOVERED + " INT, " +
                    Information.TIME_UPDATE + " TEXT)"

        private const val DROP_TABLE_INFORMATION = "DROP TABLE IF EXISTS " + Information.TABLE_NAME

        private val lock = Any()
        private var instance: MyDatabase? = null
        fun getInstance(context: Context?) =
            instance ?: synchronized(lock) {
                instance ?: MyDatabase(context, DB_NAME, DB_VERSION).also { instance = it }
            }
    }
}
