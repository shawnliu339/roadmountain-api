package com.roadmountain.sim.validator

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ValidatorController(
    private val validatorService: ValidatorService
) {
    @GetMapping("/validators/sims/{simNo}/unique")
    fun uniqueSimNumber(@PathVariable simNo: String): Boolean {
        return validatorService.uniqueSimNumber(simNo)
    }
}