package com.example.demo

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@EnableAsync
@SpringBootApplication
class App: AsyncConfigurer {
    @Override
    override fun getAsyncExecutor(): Executor =
        ThreadPoolTaskExecutor().apply {
            this.corePoolSize = 2
            this.maxPoolSize = 100
            this.setQueueCapacity(5)
            this.initialize()
        }

    @Override
    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler =
        SimpleAsyncUncaughtExceptionHandler()
}
