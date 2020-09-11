package com.sunasterisk.anticovid_19.utils

object ModelConst {
    const val NEW_CONFIRMED = "NewConfirmed"
    const val NEW_DEATHS = "NewDeaths"
    const val NEW_RECOVERED = "NewRecovered"
    const val TOTAL_CONFIRMED = "TotalConfirmed"
    const val TOTAL_DEATHS = "TotalDeaths"
    const val TOTAL_RECOVERED = "TotalRecovered"
    const val TITLE_NEWS = "title"
    const val TIME_NEWS = "pubDate"
    const val LINK_NEWS = "link"
}

object NameConst {
    const val COUNTRIES = "Countries"
    const val GLOBAL = "Global"
    const val VIET_NAM = "Viet Nam"
    const val COVID = "Covid-19"
    const val ITEM = "item"
    const val DESCRIPTION = "description"
    const val HEADACHE = "Headache"
    const val COUGH = "Cough"
    const val FEVER = "Fever"
    const val WEAR_MASH = "Wear face mask"
    const val WASH_HANDS = "Wash your hand"
}

object LinkConst {
    const val COVID_API = "https://api.covid19api.com/summary"
    const val VNEXPRESS_RSS = "https://vnexpress.net/rss/the-gioi.rss"
}

object TimeConst {
    const val INPUT_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val OUTPUT_TIME_FORMAT = "HH:mm dd/MM/yyyy"
    const val TIME_NEWS_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z"
    const val ID_TIMEZONE = "UTC"
}

object PreferencesConst {
    const val PREFS_NAME = "PREFS"
    const val PREFS_IS_ALLOW_NOTIFICATION = "PREFS_IS_ALLOW_NOTIFICATION"
    const val PREFS_IS_VIETNAMESE_LANGUAGE = "PREFS_IS_VIETNAMESE_LANGUAGE"
}

object FragmentConst {
    const val BUNDLE_ACTION = "BUNDLE_ACTION"
    const val BUNDLE_NAME_TAB = "BUNDLE_NAME_TAB"
    const val BUNDLE_SHOULD_ADD = "BUNDLE_SHOULD_ADD"
    const val BUNDLE_COUNTRY = "BUNDLE_COUNTRY"
    const val BUNDLE_IS_VIETNAM = "BUNDLE_IS_VIETNAM"
    const val TAB_HOME = "tab_home"
    const val TAB_STATISTICS = "tab_statistics"
    const val TAB_NEWS = "tab_news"
    const val TAB_DETAIL_COUNTRIES = "tab_detail_countries"
    const val BUNDLE_IS_ROOT_FRAGMENT = "BUNDLE_IS_ROOT_FRAGMENT"
}
