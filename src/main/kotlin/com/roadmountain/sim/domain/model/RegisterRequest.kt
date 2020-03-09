package com.roadmountain.sim.domain.model

import com.roadmountain.sim.domain.entity.Registration
import com.roadmountain.sim.domain.entity.Registration.RegistrationPrivacy
import com.roadmountain.sim.domain.enum.CustomerSuffix
import java.time.LocalDate
import javax.validation.constraints.Email
import javax.validation.constraints.Size

data class RegisterRequest(
    val suffix: CustomerSuffix,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    @field:Size(min = 13, max = 13)
    val simNo: String,
    @field:Size(min = 7, max = 7)
    val orderId: String,
    val passportNo: String,
    val passportExpiry: LocalDate,
    val passportCountry: String,
    val address: String,
    val dateOfBirth: LocalDate,
    @field:Email
    val email: String,
    val brand: String,
    val plan: String
) {
    fun toEntity(): Registration {
        return Registration(
            suffix = suffix,
            orderId = orderId,
            simNo = simNo,
            passportCountry = passportCountry,
            dateOfBirth = dateOfBirth,
            brand = brand,
            plan = plan,
            privacy = RegistrationPrivacy(
                firstName = firstName,
                middleName = middleName,
                lastName = lastName,
                passportNo = passportNo,
                passportExpiry = passportExpiry,
                address = address,
                email = email
            )
        )
    }
}