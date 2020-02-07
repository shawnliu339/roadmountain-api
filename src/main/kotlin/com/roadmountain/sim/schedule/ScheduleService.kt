package com.roadmountain.sim.schedule

import com.roadmountain.sim.gmail.GmailService
import com.roadmountain.sim.register.CsvService
import org.springframework.stereotype.Service

@Service
class ScheduleService(
    private val csvService: CsvService,
    private val gmailService: GmailService
) {
    fun sendCsvToProvider() {
        csvService.createCsvString()
    }
}