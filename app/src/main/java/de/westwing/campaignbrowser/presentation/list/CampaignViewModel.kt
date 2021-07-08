package de.westwing.campaignbrowser.presentation.list

import de.westwing.campaignbrowser.common.NoInternetConnectionException
import de.westwing.campaignbrowser.domain.GetCampaignListUseCase
import de.westwing.campaignbrowser.domain.NetworkManager
import de.westwing.campaignbrowser.domain.model.Campaign
import de.westwing.campaignbrowser.presentation.base.BaseViewModel
import javax.inject.Inject

class CampaignViewModel @Inject constructor(
    private val getCampaignListUseCase: GetCampaignListUseCase,
    private val networkManager: NetworkManager
) : BaseViewModel<ListViewState>() {


    fun getCampaignList() {
        liveData.postValue(ListViewState.Loading)
        if (networkManager.hasNetwork()) {
            getCampaignListUseCase.execute().subscribe(
                { response ->
                    liveData.postValue(ListViewState.Loaded(response))
                }, {
                    liveData.postValue(ListViewState.Error(it))
                }
            )

        } else {
            liveData.postValue(ListViewState.Error(NoInternetConnectionException()))
        }

    }
}

sealed class ListViewState {
    object Loading : ListViewState()
    data class Loaded(val campaigns: List<Campaign>) : ListViewState()
    data class Error(val throwable: Throwable) : ListViewState()
}