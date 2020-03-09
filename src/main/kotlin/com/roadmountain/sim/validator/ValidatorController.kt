package com.roadmountain.sim.validator

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ValidatorController(
    private val validatorService: ValidatorService
) {
    @GetMapping("/api/validators/sims/{simNo}/unique")
    fun uniqueSimNumber(@PathVariable simNo: String): Boolean {
        return validatorService.uniqueSimNumber(simNo)
    }

    @GetMapping("/api/validators/orderids/{orderId}/exist")
    fun existingOrderId(@PathVariable orderId: String): Boolean {
        return validatorService.existingOrderId(orderId)
    }

    @GetMapping("/api/validators/orderids/{orderId}/unique")
    fun uniqueOrderId(@PathVariable orderId: String): Boolean {
        return validatorService.uniqueOrderId(orderId)
    }
}