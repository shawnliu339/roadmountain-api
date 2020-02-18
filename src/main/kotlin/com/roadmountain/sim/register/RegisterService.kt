package com.roadmountain.sim.register

import com.roadmountain.sim.domain.entity.Customer
import com.roadmountain.sim.gmail.GmailService
import com.roadmountain.sim.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class RegisterService(
    private val customerRepository: CustomerRepository,
    private val gmailService: GmailService
) {
    fun register(customer: Customer): Customer {
        val savedCustomer = customerRepository.save(customer)
        val content = gmailService.createEmail(
            to = savedCustomer.email,
            subject = "test",
            bodyText = "test\n  test\ntest"
        )
        gmailService.sendMessage(content)
        return savedCustomer
    }

    fun getCountries(): Map<String, String> {
        return Locale.getISOCountries().map {
            val locale = Locale("en", it)
            locale.country to locale.displayCountry
        }.toMap()
    }
}