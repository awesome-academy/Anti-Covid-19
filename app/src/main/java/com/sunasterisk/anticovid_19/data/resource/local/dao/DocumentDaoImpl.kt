package com.sunasterisk.anticovid_19.data.resource.local.dao

import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.data.model.Document
import com.sunasterisk.anticovid_19.utils.NameConst.COUGH
import com.sunasterisk.anticovid_19.utils.NameConst.FEVER
import com.sunasterisk.anticovid_19.utils.NameConst.HEADACHE
import com.sunasterisk.anticovid_19.utils.NameConst.WASH_HANDS
import com.sunasterisk.anticovid_19.utils.NameConst.WEAR_MASH

class DocumentDaoImpl : DocumentDao {
    override fun getSymptoms(): List<Document> = mutableListOf(
        Document(R.drawable.headache, HEADACHE),
        Document(R.drawable.cough, COUGH),
        Document(R.drawable.fever, FEVER)
    )

    override fun getPrevents(): List<Document> = mutableListOf(
        Document(R.drawable.wear_mask, WEAR_MASH),
        Document(R.drawable.wash_hands, WASH_HANDS)
    )
}
