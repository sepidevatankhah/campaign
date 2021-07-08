package de.westwing.campaignbrowser.domain.executer

import io.reactivex.rxjava3.core.Scheduler

/**
 * Represents an asynchronous execution for [interactor.ObservableUseCase].
 * It's useful to execute use cases out of
 * the UI thread to prevent it from freezing.
 */
interface UseCaseExecutor {
    val scheduler: Scheduler
}