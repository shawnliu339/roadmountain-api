package com.roadmountain.sim.register.config

import com.roadmountain.sim.common.properties.RegistrationMailProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    RegistrationMailProperties::class
)
class RegistrationConfiguration