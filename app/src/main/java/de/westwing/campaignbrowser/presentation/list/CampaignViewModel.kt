package de.westwing.campaignbrowser.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.westwing.campaignbrowser.domain.model.Campaign
import de.westwing.campaignbrowser.domain.GetCampaignListUseCase
import javax.inject.Inject

class CampaignViewModel @Inject constructor(
    private val getCampaignListUseCase: GetCampaignListUseCase
) : ViewModel() {

    val campaignsData = MutableLiveData<List<Campaign>>()

    //TODO
}