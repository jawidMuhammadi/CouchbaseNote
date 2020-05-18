package com.spotlightapps.couchbasenote

import androidx.lifecycle.Observer
import java.util.*

/**
 * Created by Ahmad Jawid Muhammadi on 17/5/20
 */
class Event<out T>(private val content: T?) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            content
        }
    }
}

class EventObserver<T>(val onEventUnHandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnHandledContent(it)
        }
    }
}