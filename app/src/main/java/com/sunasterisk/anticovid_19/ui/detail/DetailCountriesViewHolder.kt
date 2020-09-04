package com.sunasterisk.anticovid_19.ui.detail

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.data.model.Country
import kotlinx.android.synthetic.main.item_recyclerview_countries.view.*

class DetailCountriesViewHolder(
    itemView: View,
    onItemClick: (Country, Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private var itemData: Country? = null

    init {
        itemView.setOnClickListener {
            itemData?.let {
                onItemClick(it, adapterPosition)
            }
        }
    }

    fun bindData(country: Country) {
        itemData = country
        itemView.run {
            textViewCountryName.text = country.country
            textViewCountryCode.text = context.getString(R.string.text_country_code, country.countryCode)
            textViewInfected.text = country.totalConfirmed.toString()
            textViewDeath.text = country.totalDeaths.toString()
            textViewRecovered.text = country.totalRecovered.toString()
        }
    }
}
