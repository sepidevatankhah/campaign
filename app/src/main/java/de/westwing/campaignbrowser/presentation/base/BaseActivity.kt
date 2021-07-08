package de.westwing.campaignbrowser.presentation.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import de.westwing.campaignbrowser.di.ViewModelFactory
import de.westwing.campaignbrowser.presentation.list.CampaignViewModel
import javax.inject.Inject

abstract class BaseActivity<ViewState, ViewModel : BaseViewModel<ViewState>> : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<CampaignViewModel>
    protected lateinit var viewModel: ViewModel


    fun createViewModel(clazz: Class<ViewModel>) {
        viewModel = ViewModelProvider(this, viewModelFactory).get(clazz)
    }

    protected fun startObserving() {
        viewModel.liveData.observe(this, Observer { state -> processViewState(state) })
    }

    abstract fun processViewState(state: ViewState)
}