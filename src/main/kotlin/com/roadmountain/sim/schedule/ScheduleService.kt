package com.roadmountain.sim.schedule

import com.roadmountain.sim.gmail.GmailService
import com.roadmountain.sim.register.CsvService
import com.roadmountain.sim.repository.RegistrationRepository
import org.springframework.data.domain.Range
import org.springframework.stereotype.Service
import java.time.*

@Service
class ScheduleService(
    private val clock: Clock,
    private val csvService: CsvService,
    private val gmailService: GmailService,
    private val repository: RegistrationRepository
) {
    companion object {
        private const val OWNER = "liulu1977@gmail.com"
        private const val PROVIDER = "sranliu1993@gmail.com"
        private val SERVICE_START_TIME = LocalDateTime.of(
            LocalDate.parse("2019-03-01"),
            LocalTime.MIN
        ).toInstant(ZoneOffset.UTC)
    }

    fun sendCsvToProvider() {
        val file = csvService.createCsvFile() ?: return
        val content = gmailService.createEmailWithAttachment(
            to = arrayOf(PROVIDER, OWNER),
            subject = "Activation list from cloud ${LocalDate.now()}",
            bodyText = "",
            file = file
        )
        gmailService.sendMessage(content)
    }

    fun deletePrivacySavedMoreThan30Days() {
        val thirtyDaysAgo = LocalDate.now(clock).minusDays(30)
        val moreThan30Days = Range.closed(
            SERVICE_START_TIME,
            LocalDateTime.of(thirtyDaysAgo, LocalTime.MAX).toInstant(ZoneOffset.UTC)
        )
        val deletedList = repository.findByCreatedBetween(moreThan30Days)
            .map { it.copy(privacy = null) }

        repository.saveAll(deletedList)
    }
}