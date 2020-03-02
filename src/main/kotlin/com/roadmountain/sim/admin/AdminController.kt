package com.roadmountain.sim.admin

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AdminController(
    private val service: AdminService
) {
    @GetMapping("/api/admin")
    fun home(): String {
        return "hello world!"
    }

    @GetMapping("/api/admin/reset")
    fun reset(): String {
        service.reset()
        return "Admin is reset successfully."
    }
}