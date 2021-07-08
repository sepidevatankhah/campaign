package de.westwing.campaignbrowser.data.extractor

import de.westwing.campaignbrowser.domain.executer.UseCaseExecutor
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * provides background thread to prevent locking ui when calling network api
 */

class NetworkJobExecutor : UseCaseExecutor {
    override val scheduler: Scheduler
        get() = Schedulers.io()
}