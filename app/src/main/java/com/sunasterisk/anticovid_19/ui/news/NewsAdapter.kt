package com.sunasterisk.anticovid_19.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.data.model.News

class NewsAdapter : RecyclerView.Adapter<NewsViewHolder>() {

    var onItemClick: (News, Int) -> Unit = { _, _ -> }
    private val news = mutableListOf<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recyclerview_news, parent, false)
        return NewsViewHolder(view, onItemClick)
    }

    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindData(news[position])
    }

    fun updateNes(newsList: List<News>) {
        news.clear()
        news.addAll(newsList)
        notifyDataSetChanged()
    }
}
