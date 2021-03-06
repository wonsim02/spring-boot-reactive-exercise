package com.example.demo.temperature.streamv0

import com.example.demo.temperature.Temperature
import org.springframework.context.event.EventListener
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.lang.Exception
import java.util.concurrent.CopyOnWriteArraySet
import javax.servlet.http.HttpServletRequest

@RestController
class TemperatureController {
    private val clients: MutableSet<SseEmitter> = CopyOnWriteArraySet()

    @RequestMapping(
        value = ["/temperature-stream"],
        method = [RequestMethod.GET]
    )
    fun events(request: HttpServletRequest): SseEmitter =
        SseEmitter().apply {
            clients.add(this)
            this.onTimeout { clients.remove(this) }
            this.onCompletion { clients.remove(this) }
        }

    @Async
    @EventListener
    fun handleMessage(temperature: Temperature) {
        val deadEmitters: MutableList<SseEmitter> = arrayListOf()
        clients.forEach {
            try {
                it.send(temperature, MediaType.APPLICATION_JSON)
            }
            catch (ignore: Exception) {
                deadEmitters.add(it)
            }
        }
        clients.removeAll(deadEmitters)
    }
}
