package com.sunasterisk.anticovid_19.ui.web

import android.content.Context
import android.content.Intent
import android.webkit.WebViewClient
import com.sunasterisk.anticovid_19.R
import com.sunasterisk.anticovid_19.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web_news.*

class WebNewsActivity : BaseActivity() {

    override val layoutResource: Int
        get() = R.layout.activity_web_news

    override fun initComponents() {
        val url = intent?.getStringExtra(EXTRA_LINK_WEB)
        webViewNews.webViewClient = WebViewClient()
        webViewNews.loadUrl(url)
    }

    companion object {
        private const val EXTRA_LINK_WEB = "EXTRA_LINK_WEB"
        fun getIntent(context: Context, link: String): Intent =
            Intent(context, WebNewsActivity::class.java)
                .putExtra(EXTRA_LINK_WEB, link)
    }
}
