package com.roadmountain.sim.schedule

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ScheduleController(
    private val scheduleService: ScheduleService
) {
    @GetMapping("schedules/csv")
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Tokyo")
    fun sendCsvToProvider() {
        scheduleService.sendCsvToProvider()
    }
}