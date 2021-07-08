package de.westwing.campaignbrowser.domain

import de.westwing.campaignbrowser.domain.executer.PostExecutionThread
import de.westwing.campaignbrowser.domain.base.SingleUseCase
import de.westwing.campaignbrowser.domain.executer.UseCaseExecutor
import de.westwing.campaignbrowser.domain.model.Campaign
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetCampaignListUseCase @Inject constructor(
    useCaseExecutor: UseCaseExecutor,
    postExecutionThread: PostExecutionThread,
    repository: CampaignRepository
) : SingleUseCase<Any, List<Campaign>>(useCaseExecutor, postExecutionThread, repository){

    override fun interact(params: Any?): Single<List<Campaign>> {
      return  repository.getCampaigns()
    }
}