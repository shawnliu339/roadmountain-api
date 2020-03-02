package com.roadmountain.sim.schedule

import com.roadmountain.sim.common.logging.LoggerDelegate
import com.roadmountain.sim.domain.entity.Registration
import com.roadmountain.sim.repository.RegistrationRepository
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.assertj.core.util.VisibleForTesting
import org.springframework.data.domain.Range
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileWriter
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class CsvService(
    private val registrationRepository: RegistrationRepository,
    private val clock: Clock
) {
    companion object {
        private val HEADERS = arrayOf(
            "Vendor Code", "Suffix", "First Name", "Middle Name", "Last Name",
            "SIM No", "Passport No", "Passport Expiry", "Passport Country",
            "Address in Australia", "Date of Birth", "Email(Optional)", "Brand", "Plan(\$)"
        )
        private const val VENDOR_CODE = ""
        private val logger by LoggerDelegate()
    }

    fun createCsvFile(): File? {
        val listInThePreviousDay = findAllInThePreviousDay()
        if (listInThePreviousDay.isEmpty()) return null

        val fileName = "/tmp/${LocalDate.now(clock)}.csv"
        val formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")

        CSVPrinter(FileWriter(fileName), CSVFormat.EXCEL.withHeader(*HEADERS)).use { printer ->
            listInThePreviousDay.forEach {
                if (it.privacy == null) {
                    logger.error("Registration privacy should not be null during creating csv string for provider email.")
                    return@forEach
                }
                printer.printRecord(
                    VENDOR_CODE, it.suffix,
                    it.privacy.firstName, it.privacy.middleName, it.privacy.lastName,
                    it.simNo,
                    it.privacy.passportNo, it.privacy.passportExpiry.format(formatter),
                    Locale("en", it.passportCountry).displayCountry,
                    it.privacy.address, it.dateOfBirth.format(formatter), it.privacy.email,
                    it.brand, it.plan
                )
            }
        }

        return File(fileName)
    }

    @VisibleForTesting
    internal fun findAllInThePreviousDay(): List<Registration> {
        val thePreviousDate = LocalDate.now(clock).minusDays(1)
        val thePreviousDay = Range.closed(
            LocalDateTime.of(thePreviousDate, LocalTime.MIN).toInstant(ZoneOffset.UTC),
            LocalDateTime.of(thePreviousDate, LocalTime.MAX).toInstant(ZoneOffset.UTC)
        )

        return registrationRepository.findByCreatedBetween(thePreviousDay)
    }
}