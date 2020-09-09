package com.sunasterisk.anticovid_19.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.data.model.Document

class HomeAdapter : RecyclerView.Adapter<HomeViewHolder>() {

    private val documents = mutableListOf<Document>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recyclerview_home, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int = documents.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bindData(documents[position])
    }

    fun update(newDocuments: List<Document>) {
        documents.clear()
        documents.addAll(newDocuments)
        notifyDataSetChanged()
    }
}
