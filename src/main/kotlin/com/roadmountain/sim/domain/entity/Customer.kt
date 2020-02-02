package com.roadmountain.sim.domain.entity

import com.roadmountain.sim.domain.enum.CustomerSuffix
import org.springframework.data.annotation.Id

data class Customer(
    @field:Id
    private val _id: String? = null,
    val vendorCode: String,
    val suffix: CustomerSuffix
) {
    val id: String
        get() {
            return checkNotNull(_id)
        }
}
