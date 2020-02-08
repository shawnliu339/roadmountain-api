package com.roadmountain.sim

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class EcApplication

fun main(args: Array<String>) {
    runApplication<EcApplication>(*args)
}
