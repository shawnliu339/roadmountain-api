package com.roadmountain.sim.schedule

import com.roadmountain.sim.common.logging.LoggerDelegate
import com.roadmountain.sim.domain.entity.Registration
import com.roadmountain.sim.repository.RegistrationRepository
import org.assertj.core.util.VisibleForTesting
import org.springframework.data.domain.Range
import org.springframework.stereotype.Service
import java.io.File
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class CsvService(
    private val registrationRepository: RegistrationRepository,
    private val clock: Clock
) {
    companion object {
        private const val TITLE: String = "Vendor Code,Suffix,First Name,Middle Name,Last Name," +
                "SIM No,Passport No,Passport Expiry,Passport Country," +
                "Address in Australia,Date of Birth,Email(Optional),Brand,Plan(\$)\n"
        private const val VENDOR_CODE = ""
        private val logger by LoggerDelegate()
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
            if (it.privacy == null) {
                logger.error("Registration privacy should not be null during creating csv string for provider email.")
                return ""
            }

            buildString {
                append("$VENDOR_CODE,${it.suffix},")
                append("${it.privacy.firstName},${it.privacy.middleName},${it.privacy.lastName},")
                append("${it.simNo},")
                append("${it.privacy.passportNo},${it.privacy.passportExpiry.format(formatter)},")
                append("${Locale("en", it.passportCountry).displayCountry},")
                append("${it.privacy.address},${it.dateOfBirth.format(formatter)},${it.privacy.email},")
                append("${it.brand},${it.plan}\n")
            }
        }.reduce { acc, string -> acc + string }

        return TITLE + rows
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