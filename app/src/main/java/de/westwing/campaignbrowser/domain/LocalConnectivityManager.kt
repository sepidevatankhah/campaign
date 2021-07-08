package de.westwing.campaignbrowser.domain

interface LocalConnectivityManager {
    fun hasNetwork(): Boolean
}