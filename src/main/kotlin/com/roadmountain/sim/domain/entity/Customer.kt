package com.roadmountain.sim.domain.entity

import com.roadmountain.sim.domain.enum.CustomerSuffix
import org.springframework.data.annotation.Id
import java.time.Instant
import java.time.LocalDate

data class Customer(
    @field:Id
    private val _id: String? = null,
    val vendorCode: String,
    val suffix: CustomerSuffix,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val simNo: String,
    val passportNo: String,
    val passportExpiry: LocalDate,
    val passportCountry: String,
    val address: String,
    val dateOfBirth: LocalDate,
    val email: String?,
    val brand: String,
    val plan: String,
    val created: Instant = Instant.now()
) {
    val id: String
        get() {
            return checkNotNull(_id)
        }
}
