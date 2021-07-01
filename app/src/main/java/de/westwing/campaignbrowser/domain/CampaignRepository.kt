package de.westwing.campaignbrowser.domain

import io.reactivex.rxjava3.core.Single

interface CampaignRepository {

    fun getCampaigns(): Single<List<Campaign>>

}