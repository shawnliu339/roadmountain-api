package com.roadmountain.sim.register

import com.google.api.services.gmail.Gmail
import com.roadmountain.sim.domain.entity.Customer
import com.roadmountain.sim.gmail.GMailService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class RegisterController(
    private val service: RegisterService,
    private val gmailService: GMailService,
    private val gmail: Gmail
) {
    @GetMapping("/")
    fun home(): String {
        return "register"
    }

    @GetMapping("/register")
    fun register(): MutableList<Customer> {
        return service.test()
    }

    @GetMapping("/mail")
    fun mail() {
        val content = gmailService.createEmail(
            to = "liulu1977@gmail.com",
            from = "you@gmail.com",
            subject = "test mail",
            bodyText = "test test!"
        )
        gmailService.sendMessage(service = gmail, userId = "me", emailContent = content)
    }
}