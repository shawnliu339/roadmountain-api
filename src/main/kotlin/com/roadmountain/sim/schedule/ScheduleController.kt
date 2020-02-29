package com.roadmountain.sim.schedule

import com.roadmountain.sim.common.logging.LoggerDelegate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ScheduleController(
    private val scheduleService: ScheduleService
) {
    companion object {
        private val logger by LoggerDelegate()
    }

    @PostMapping("/api/schedules/csv")
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Tokyo")
    fun sendCsvToProvider() {
        logger.info("Start to send CSV to the provider.")
        scheduleService.sendCsvToProvider()
    }

    @DeleteMapping("/api/schedules/privacy/30days")
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Tokyo")
    fun deletePrivacySavedMoreThan30Days() {
        logger.info("Start privacy deleting batch")
        scheduleService.deletePrivacySavedMoreThan30Days()
    }
}