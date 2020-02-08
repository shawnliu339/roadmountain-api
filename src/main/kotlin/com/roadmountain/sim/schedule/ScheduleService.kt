package com.roadmountain.sim.schedule

import com.roadmountain.sim.gmail.GmailService
import com.roadmountain.sim.register.CsvService
import org.springframework.stereotype.Service

@Service
class ScheduleService(
    private val csvService: CsvService,
    private val gmailService: GmailService
) {
    companion object {
        private const val OWNER = "liulu1977@gmail.com"
        private const val PROVIDER = "sranliu1993@gmail.com"
    }

    fun sendCsvToProvider() {
        val file = csvService.createCsvFile() ?: return
        val content = gmailService.createEmailWithAttachment(
            to = arrayOf(PROVIDER, OWNER),
            from = "you@gmail.com",
            subject = "test mail",
            bodyText = "test test!",
            file = file
        )
        gmailService.sendMessage(userId = "me", emailContent = content)
    }
}