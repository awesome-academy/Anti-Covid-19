package com.sunasterisk.anticovid_19.data.resource.local.dao

import com.sunasterisk.anticovid_19.data.model.Information
import com.sunasterisk.anticovid_19.data.resource.local.db.MyDatabase

class InformationDaoImpl private constructor(database: MyDatabase): InformationDao {

    private val writableDb = database.writableDatabase
    private val readableDb = database.readableDatabase

    override fun getLastInformation(): Information? {
        val cursor = readableDb.query(
            Information.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            ORDER_BY,
            LIMIT
        )
        return cursor?.use { if (cursor.moveToFirst()) Information(it) else null}
    }

    override fun addInformation(information: Information) {
        val cursor = writableDb.query(
            Information.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
            )
        if (cursor.count == 0)
            writableDb.insert(
                Information.TABLE_NAME,
                null,
                information.getContentValues())
        cursor.close()
    }

    override fun updateInformation(information: Information) {
        writableDb.update(
            Information.TABLE_NAME,
            information.getContentValues(),
            Information.ID + " = " + information.id,
            null
        )
    }

    companion object {
        private const val LIMIT = "1"
        private const val ORDER_BY = Information.ID + " DESC"

        private var instance: InformationDaoImpl? = null
        fun getInstance(database: MyDatabase) =
            instance ?: InformationDaoImpl(database).also { instance = it }
    }
}
