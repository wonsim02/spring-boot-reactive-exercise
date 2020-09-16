package com.example.demo.utils

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import rx.Subscriber
import java.io.IOException
import java.io.Serializable

class RxSseEmitter<T: Serializable>: SseEmitter(SSE_SESSION_TIMEOUT) {
    companion object {
        const val SSE_SESSION_TIMEOUT = 30 * 60 * 1000L
    }

    private val subscriber = object : Subscriber<T>() {
        override fun onNext(t: T) {
            try {
                this@RxSseEmitter.send(t)
            } catch (e: IOException) {
                unsubscribe()
            }
        }

        override fun onCompleted() { }

        override fun onError(e: Throwable?) { }
    }

    init {
        onCompletion(subscriber::unsubscribe)
        onTimeout(subscriber::unsubscribe)
    }

    fun getSubscriber() = subscriber
}