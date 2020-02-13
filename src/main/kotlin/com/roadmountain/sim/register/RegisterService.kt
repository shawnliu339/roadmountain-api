package com.roadmountain.sim.register

import com.roadmountain.sim.domain.entity.Customer
import com.roadmountain.sim.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class RegisterService(
    private val customerRepository: CustomerRepository
) {
    fun save(customer: Customer): Customer {
        return customerRepository.save(customer)
    }

    fun getCountries(): Map<String, String> {
        return Locale.getISOCountries().map {
            val locale = Locale("en", it)
            locale.country to locale.displayCountry
        }.toMap()
    }
}