package com.roadmountain.sim.domain.model

import com.roadmountain.sim.domain.entity.Customer
import com.roadmountain.sim.domain.enum.CustomerSuffix
import java.time.LocalDate
import javax.validation.constraints.Email

data class RegisterRequest(
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
    @field:Email
    val email: String?,
    val brand: String,
    val plan: String
) {
    fun toEntity(): Customer {
        return Customer(
            suffix = suffix,
            firstName = firstName,
            middleName = middleName,
            lastName = lastName,
            simNo = simNo,
            passportNo = passportNo,
            passportExpiry = passportExpiry,
            passportCountry = passportCountry,
            address = address,
            dateOfBirth = dateOfBirth,
            email = email,
            brand = brand,
            plan = plan
        )
    }
}