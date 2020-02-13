package com.roadmountain.sim.repository

import com.roadmountain.sim.common.TestBase
import com.roadmountain.sim.domain.entity.Customer
import com.roadmountain.sim.domain.enum.CustomerSuffix
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Range
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit.DAYS
import org.assertj.core.api.Assertions.assertThat as assertThat1


class CustomerRepositoryTest : TestBase() {
    @Autowired
    lateinit var target: CustomerRepository
    private val clock: Clock = Clock.fixed(Instant.parse("2010-10-10T00:00:00Z"), ZoneOffset.UTC)

    @Test
    fun testFindByCreatedBetween() {
        // Prepare
        val customer1 = Customer(
            suffix = CustomerSuffix.MISS,
            firstName = "testFirstName",
            middleName = "testMiddleName",
            lastName = "testLastName",
            simNo = "testSimNo",
            passportNo = "testPassportNo",
            passportExpiry = LocalDate.parse("2010-11-10"),
            passportCountry = "testPassportCountry",
            address = "testAddress",
            dateOfBirth = LocalDate.parse("2010-10-10"),
            email = "testEmail",
            brand = "testBrand",
            plan = "testPlan",
            created = Instant.now(clock)
        )
        val customer2 = customer1.copy(created = Instant.now(clock).minus(10, DAYS))
        val customer3 = customer1.copy(created = Instant.now(clock))

        target.save(customer2)
        val expected = target.saveAll(listOf(customer1, customer3))

        // Do
        val actual = target.findByCreatedBetween(
            Range.closed(Instant.now(clock), Instant.now(clock))
        )

        // Verify
        assertThat1(actual).isEqualTo(expected)
    }
}