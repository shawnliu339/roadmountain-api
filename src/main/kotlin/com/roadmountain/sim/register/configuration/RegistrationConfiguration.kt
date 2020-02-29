package com.roadmountain.sim.register.configuration

import com.roadmountain.sim.properties.RegistrationMailProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    RegistrationMailProperties::class
)
class RegistrationConfiguration