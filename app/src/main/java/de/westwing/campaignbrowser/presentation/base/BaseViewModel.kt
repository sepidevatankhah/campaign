package de.westwing.campaignbrowser.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<ViewState> : ViewModel() {
    val liveData = MutableLiveData<ViewState>()
}
