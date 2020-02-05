package com.roadmountain.sim.repository

import com.roadmountain.sim.common.TestBase
import com.roadmountain.sim.domain.entity.Customer
import com.roadmountain.sim.domain.enum.CustomerSuffix
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Range
import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat as assertThat1


class CustomerRepositoryTest : TestBase() {
    @Autowired
    lateinit var target: CustomerRepository

    @Test
    fun testFindByDateOfBirthBetween() {
        // Prepare
        val customer1 = Customer(
            vendorCode = "testVendorCode",
            suffix = CustomerSuffix.MISS,
            firstName = "testFirstName",
            middleName = "testMiddleName",
            lastName = "testLastName",
            simNo = "testSimNo",
            passportNo = "testPassportNo",
            passportExpiry = "testPassportExpiry",
            passportCountry = "testPassportCountry",
            address = "testAddress",
            dateOfBirth = LocalDate.parse("2010-10-10"),
            email = "testEmail",
            brand = "testBrand",
            plan = "testPlan"
        )
        val customer2 = customer1.copy(dateOfBirth = LocalDate.parse("2010-10-01"))
        val customer3 = customer1.copy(dateOfBirth = LocalDate.parse("2010-10-10"))

        target.save(customer2)
        val expected = target.saveAll(listOf(customer1, customer3))

        // Do
        val actual = target.findByDateOfBirthBetween(
            Range.closed(LocalDate.parse("2010-10-10"), LocalDate.parse("2010-10-10"))
        )

        // Verify
        assertThat1(actual).isEqualTo(expected)
    }
}