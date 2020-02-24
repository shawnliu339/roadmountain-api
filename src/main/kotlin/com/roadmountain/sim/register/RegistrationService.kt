package com.roadmountain.sim.register

import com.roadmountain.sim.domain.entity.Registration
import com.roadmountain.sim.gmail.GmailService
import com.roadmountain.sim.repository.RegistrationRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class RegistrationService(
    private val registrationRepository: RegistrationRepository,
    private val gmailService: GmailService
) {
    fun register(registration: Registration) {
        requireNotNull(registration.privacy) { "Registration privacy should not be null during register moment." }

        registrationRepository.save(registration)
        val content = gmailService.createEmail(
            to = registration.privacy.email,
            subject = "test",
            bodyText = "test\n  test\ntest"
        )
        gmailService.sendMessage(content)
    }

    fun getCountries(): Map<String, String> {
        return Locale.getISOCountries().map {
            val locale = Locale("en", it)
            locale.country to locale.displayCountry
        }.toMap()
    }
}