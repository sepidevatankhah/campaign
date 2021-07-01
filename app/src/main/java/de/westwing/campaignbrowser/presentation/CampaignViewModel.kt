package de.westwing.campaignbrowser.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.westwing.campaignbrowser.domain.Campaign
import de.westwing.campaignbrowser.domain.GetCampaignListUseCase
import javax.inject.Inject

class CampaignViewModel @Inject constructor(
    private val getCampaignListUseCase: GetCampaignListUseCase
) : ViewModel() {

    val campaignsData = MutableLiveData<List<Campaign>>()

    //TODO
}