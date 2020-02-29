package com.roadmountain.sim.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("registration.mail")
@ConstructorBinding
data class RegistrationMailProperties(
    val ownerAddress: String,
    val providerAddress: String,
    val csvTitle: String,
    val activateTitle: String
)