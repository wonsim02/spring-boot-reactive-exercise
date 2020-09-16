package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(
    basePackages = ["com.example.demo.temperature.streamv1"]
)
@SpringBootApplication
class AppV1
