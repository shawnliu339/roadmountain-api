package com.roadmountain.sim.repository

import com.roadmountain.sim.common.TestBase
import com.roadmountain.sim.domain.entity.Registration
import com.roadmountain.sim.domain.enum.CustomerSuffix
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Range
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit.DAYS


class RegistrationRepositoryTest : TestBase() {
    @Autowired
    lateinit var target: RegistrationRepository
    private val clock: Clock = Clock.fixed(Instant.parse("2010-10-10T00:00:00Z"), ZoneOffset.UTC)

    @Test
    fun testFindByCreatedBetween() {
        // Prepare
        val customer1 = Registration(
            suffix = CustomerSuffix.MISS,
            simNo = "testSimNo",
            passportCountry = "testPassportCountry",
            dateOfBirth = LocalDate.parse("2010-10-10"),
            brand = "testBrand",
            plan = "testPlan",
            created = Instant.now(clock),
            privacy = Registration.RegistrationPrivacy(
                firstName = "testFirstName",
                middleName = "testMiddleName",
                lastName = "testLastName",
                passportNo = "testPassportNo",
                passportExpiry = LocalDate.parse("2010-11-10"),
                address = "testAddress",
                email = "testEmail"
            )
        )
        val customer2 = customer1.copy(
            created = Instant.now(clock).minus(10, DAYS),
            simNo = "testSimNo2"
        )
        val customer3 = customer1.copy(
            created = Instant.now(clock),
            simNo = "testSimNo3"
        )

        target.save(customer2)
        val expected = target.saveAll(listOf(customer1, customer3))

        // Do
        val actual = target.findByCreatedBetween(
            Range.closed(Instant.now(clock), Instant.now(clock))
        )

        // Verify
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testFindBySimNo() {
        // Prepare
        val customer1 = Registration(
            suffix = CustomerSuffix.MISS,
            simNo = "testSimNo",
            passportCountry = "testPassportCountry",
            dateOfBirth = LocalDate.parse("2010-10-10"),
            brand = "testBrand",
            plan = "testPlan",
            created = Instant.now(clock),
            privacy = Registration.RegistrationPrivacy(
                firstName = "testFirstName",
                middleName = "testMiddleName",
                lastName = "testLastName",
                passportNo = "testPassportNo",
                passportExpiry = LocalDate.parse("2010-11-10"),
                address = "testAddress",
                email = "testEmail"
            )
        )
        val customer2 = customer1.copy(simNo = "test")
        target.save(customer2)
        val expected = target.save(customer1)

        val actual = target.findBySimNo("testSimNo")

        assertThat(actual).isEqualTo(expected)
    }
}