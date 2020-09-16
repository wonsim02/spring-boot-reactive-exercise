package com.example.demo.temperature.streamv1

import com.example.demo.temperature.Temperature
import com.example.demo.utils.RxSseEmitter
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import javax.servlet.http.HttpServletRequest

@RestController
class TemperatureController(
    private val temperatureSensor: TemperatureSensor
) {
    @RequestMapping(
        value = ["/temperature-stream"],
        method = [RequestMethod.GET]
    )
    fun events(request: HttpServletRequest): SseEmitter =
        RxSseEmitter<Temperature>().apply {
            temperatureSensor.temperatureStream()
                .subscribe(getSubscriber())
        }
}
