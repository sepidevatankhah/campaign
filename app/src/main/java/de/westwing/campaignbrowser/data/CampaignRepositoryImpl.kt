package de.westwing.campaignbrowser.data

import de.westwing.campaignbrowser.domain.Campaign
import de.westwing.campaignbrowser.domain.CampaignRepository
import io.reactivex.rxjava3.core.Single

class CampaignRepositoryImpl(private val apiInterface: ApiInterface) : CampaignRepository {

    override fun getCampaigns(): Single<List<Campaign>> {
        // TODO replace with real API call
        TODO()
    }
}