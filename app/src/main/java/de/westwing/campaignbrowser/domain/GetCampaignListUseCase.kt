package de.westwing.campaignbrowser.domain

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetCampaignListUseCase @Inject constructor() {

    fun execute(): Single<List<Campaign>> {
        TODO()
    }
}