package com.roadmountain.sim.domain.entity

import com.google.api.client.util.Maps
import org.springframework.data.annotation.Id

data class GoogleCredential(
    @field:Id
    private val _id: String? = null,
    val keyValueMap: HashMap<String, ByteArray> = Maps.newHashMap()
)