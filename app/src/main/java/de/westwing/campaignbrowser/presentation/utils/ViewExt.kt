package de.westwing.campaignbrowser.presentation.utils

import android.view.View
import android.widget.Toast
import de.westwing.campaignbrowser.R


fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.toastNoInternetConnection() {
    Toast.makeText(
        context,
        resources?.getString(R.string.no_internet_connection),
        Toast.LENGTH_SHORT
    ).show()
}

fun View.toastOopsError() {
    Toast.makeText(
        context,
        resources?.getString(R.string.oops),
        Toast.LENGTH_SHORT
    ).show()
}