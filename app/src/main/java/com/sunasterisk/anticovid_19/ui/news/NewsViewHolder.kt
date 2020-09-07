package com.sunasterisk.anticovid_19.ui.news

import android.text.format.DateUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sunasterisk.anticovid_19.data.model.News
import com.sunasterisk.anticovid_19.utils.TimeConst.TIME_NEWS_FORMAT
import kotlinx.android.synthetic.main.item_recyclerview_news.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NewsViewHolder(
    itemView: View,
    onItemClick: (News, Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private var itemData: News? = null

    init {
        itemView.setOnClickListener {
            itemData?.let {
                onItemClick(it, adapterPosition)
            }
        }
    }

    fun bindData(news: News) {
        itemData = news
        itemView.run {
            Glide.with(context).load(news.imgUrl).into(imageViewNews)
            textViewTitle.text = news.title
            textViewTime.text = DateUtils.getRelativeTimeSpanString(
                getTimeMillis(news.time),
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS
            )
        }
    }

    private fun getTimeMillis(time: String): Long {
        val input = SimpleDateFormat(TIME_NEWS_FORMAT, Locale.ENGLISH)
        val date: Date?
        try {
            date = input.parse(time)
            return date.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }
}
