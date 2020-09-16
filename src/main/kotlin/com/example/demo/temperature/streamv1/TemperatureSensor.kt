package com.example.demo.temperature.streamv1

import com.example.demo.temperature.Temperature
import org.springframework.stereotype.Component
import rx.Observable
import java.util.Random
import java.util.concurrent.TimeUnit

@Component
class TemperatureSensor {
    private val rnd = Random()

    private val dataStream: Observable<Temperature> =
        Observable.range(0, Int.MAX_VALUE)
            .concatMap {
                Observable.just(it)
                    .delay(rnd.nextInt(5000).toLong(), TimeUnit.MILLISECONDS)
                    .map { probe() }
            }
            .publish()
            .refCount()

    private fun probe() = Temperature(16 + rnd.nextGaussian() * 10)

    fun temperatureStream() = dataStream
}
