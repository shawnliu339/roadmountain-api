package com.roadmountain.sim.register

import com.roadmountain.sim.domain.model.RegisterRequest
import com.roadmountain.sim.gmail.GmailService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class RegisterController(
    private val service: RegistrationService,
    private val gmailService: GmailService
) {
    @PostMapping("/api/registers")
    fun register(@Validated @RequestBody request: RegisterRequest) {
        service.register(request.toEntity())
    }

    @GetMapping("/api/countries")
    fun countries(): Map<String, String> {
        return service.getCountries()
    }
}