package com.sunasterisk.anticovid_19.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.data.model.Country

class DetailCountriesAdapter : RecyclerView.Adapter<DetailCountriesViewHolder>() {

    var onItemClick: (Country, Int) -> Unit = { _, _ -> }
    private val countries = mutableListOf<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailCountriesViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recyclerview_countries, parent, false)
        return DetailCountriesViewHolder(view, onItemClick)
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: DetailCountriesViewHolder, position: Int) {
        holder.bindData(countries[position])
    }

    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }
}
