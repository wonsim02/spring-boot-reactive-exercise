package com.example.demo.temperature

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.Random
import javax.annotation.PostConstruct

@Component
class TemperatureSensor(private val publisher: ApplicationEventPublisher) {
    private val rnd = Random()
    private val executor = Executors.newSingleThreadScheduledExecutor()

    @PostConstruct
    fun startProcessing() {
        this.executor.schedule(this::probe, 1, TimeUnit.SECONDS)
    }

    private fun probe() {
        val temperature: Double = 16 + rnd.nextGaussian() * 10
        publisher.publishEvent(Temperature(temperature))
        executor.schedule(this::probe, rnd.nextInt(5000).toLong(), TimeUnit.MILLISECONDS)
    }
}
