package com.sunasterisk.anticovid_19.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sunasterisk.anticovid_19.data.model.Document
import kotlinx.android.synthetic.main.item_recyclerview_home.view.*

class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var itemData: Document? = null

    fun bindData(document: Document) {
        itemData = document
        itemView.run {
            imageViewPreview.setImageResource(document.image)
            textViewTitle.text = document.title
        }
    }
}
