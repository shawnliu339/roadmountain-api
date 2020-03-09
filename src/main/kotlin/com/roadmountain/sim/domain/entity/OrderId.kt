package com.roadmountain.sim.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
data class OrderId(
    @field:Id
    val orderId: String,
    val updated: Instant = Instant.now(),
    val created: Instant = Instant.now()
)