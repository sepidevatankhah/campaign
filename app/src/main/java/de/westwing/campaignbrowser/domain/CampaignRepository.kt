package de.westwing.campaignbrowser.domain

import de.westwing.campaignbrowser.domain.model.Campaign
import io.reactivex.rxjava3.core.Single

interface CampaignRepository {

    fun getCampaigns(): Single<List<Campaign>>

}