package de.westwing.campaignbrowser.common

/**
 *   Class created for handling no internet connection callbacks
 * */
data class NoInternetConnectionException(override val message: String = "There is no internet connection") : Exception()