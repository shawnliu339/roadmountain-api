package com.roadmountain.sim.register

import com.roadmountain.sim.domain.entity.Customer
import com.roadmountain.sim.repository.CustomerRepository
import org.assertj.core.util.VisibleForTesting
import org.springframework.data.domain.Range
import org.springframework.stereotype.Service
import java.io.File
import java.time.*
import java.time.format.DateTimeFormatter

@Service
class CsvService(
    private val customerRepository: CustomerRepository,
    private val clock: Clock
) {
    companion object {
        private const val TITLE: String = "Vendor Code,Suffix,First Name,Middle Name,Last Name," +
                "SIM No,Passport No,Passport Expiry,Passport Country," +
                "Address in Australia,Date of Birth,Email(Optional),Brand,Plan(\$)\n"
        private const val VENDOR_CODE = ""
    }

    fun createCsvFile(): File? {
        val csvString = createCsvString() ?: return null

        val file = File("/tmp/${LocalDate.now(clock)}.csv")
        file.writeText(csvString)
        return file
    }

    @VisibleForTesting
    internal fun createCsvString(): String? {
        val listInThePreviousDay = findAllInThePreviousDay()
        if (listInThePreviousDay.isEmpty()) return null

        val formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
        val rows = listInThePreviousDay.map {
            buildString {
                append("$VENDOR_CODE,${it.suffix},${it.firstName},${it.middleName},${it.lastName},")
                append("${it.simNo},${it.passportNo},${it.passportExpiry.format(formatter)},${it.passportCountry},")
                append("${it.address},${it.dateOfBirth.format(formatter)},${it.email},${it.brand},${it.plan}\n")
            }
        }.reduce { acc, string -> acc + string }

        return TITLE + rows
    }

    @VisibleForTesting
    internal fun findAllInThePreviousDay(): List<Customer> {
        val thePreviousDate = LocalDate.now(clock).minusDays(1)
        val thePreviousDay = Range.closed(
            LocalDateTime.of(thePreviousDate, LocalTime.MIN).toInstant(ZoneOffset.UTC),
            LocalDateTime.of(thePreviousDate, LocalTime.MAX).toInstant(ZoneOffset.UTC)
        )

        return customerRepository.findByCreatedBetween(thePreviousDay)
    }
}