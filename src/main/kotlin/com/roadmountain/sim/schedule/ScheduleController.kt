package com.roadmountain.sim.schedule

import com.roadmountain.sim.logging.LoggerDelegate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ScheduleController(
    private val scheduleService: ScheduleService
) {
    companion object {
        private val logger by LoggerDelegate()
    }

    @GetMapping("/schedules/csv")
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Tokyo")
    fun sendCsvToProvider() {
        logger.info("Start to send CSV to the provider.")
        scheduleService.sendCsvToProvider()
    }
}