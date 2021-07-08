package de.westwing.campaignbrowser.domain.executer

import io.reactivex.rxjava3.core.Scheduler

/**
 * When the use case execution is done in its executor thread, It's timeMillis to update UI on the Event
 * Dispatch thread.
 * An implementation of this interface will change the execution context and update the UI.
 */
interface PostExecutionThread {
    val scheduler: Scheduler
}