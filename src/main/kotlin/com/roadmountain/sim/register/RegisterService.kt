package com.roadmountain.sim.register

import com.roadmountain.sim.domain.entity.Customer
import com.roadmountain.sim.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class RegisterService(
    private val customerRepository: CustomerRepository
) {
    fun save(customer: Customer): Customer {
        return customerRepository.save(customer)
    }
}