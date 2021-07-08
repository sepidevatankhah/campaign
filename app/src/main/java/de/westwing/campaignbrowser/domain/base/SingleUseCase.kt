package de.westwing.campaignbrowser.domain.base

import de.westwing.campaignbrowser.domain.executer.PostExecutionThread
import de.westwing.campaignbrowser.domain.CampaignRepository
import de.westwing.campaignbrowser.domain.executer.UseCaseExecutor
import io.reactivex.rxjava3.core.Single

/**
 * @param Responses The response value emitted by the Observable.
 * @param Params The request value.
 */
abstract class SingleUseCase<Params : Any?, Responses>(
    useCaseExecutor: UseCaseExecutor,
    postExecutionThread: PostExecutionThread,
    protected var repository: CampaignRepository
) :
    UseCase<Single<Responses>, Params>(useCaseExecutor, postExecutionThread) {

    open fun execute(params: Params? = null): Single<Responses> {
        return interact(params).applySchedulers()
    }

    protected abstract fun interact(params: Params? = null): Single<Responses>

}