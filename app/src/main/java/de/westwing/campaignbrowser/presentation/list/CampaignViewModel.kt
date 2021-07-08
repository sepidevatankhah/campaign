package de.westwing.campaignbrowser.presentation.list

import de.westwing.campaignbrowser.domain.GetCampaignListUseCase
import de.westwing.campaignbrowser.domain.model.Campaign
import de.westwing.campaignbrowser.presentation.base.BaseViewModel
import javax.inject.Inject

class CampaignViewModel @Inject constructor(
    private val getCampaignListUseCase: GetCampaignListUseCase
) : BaseViewModel<ListViewState>() {


    fun getCampaignList() {
        liveData.postValue(ListViewState.Loading)
        getCampaignListUseCase.execute().subscribe(
            { response ->
                liveData.postValue(ListViewState.Loaded(response))
            }, {
                liveData.postValue(ListViewState.Error(it))
            }
        )
    }
}

sealed class ListViewState {
    object Loading : ListViewState()
    data class Loaded(val campaigns: List<Campaign>) : ListViewState()
    data class Error(val throwable: Throwable) : ListViewState()
}