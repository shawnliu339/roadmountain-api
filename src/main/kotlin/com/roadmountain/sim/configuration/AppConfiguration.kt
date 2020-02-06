package com.roadmountain.sim.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class AppConfiguration {
    @Bean
    fun clock(): Clock {
        return Clock.systemUTC()
    }
}