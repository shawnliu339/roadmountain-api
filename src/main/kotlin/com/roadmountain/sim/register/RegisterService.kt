package com.roadmountain.sim.register

import com.roadmountain.sim.domain.entity.Customer
import com.roadmountain.sim.domain.enum.CustomerSuffix
import com.roadmountain.sim.repository.CustomerRepository
import org.assertj.core.internal.bytebuddy.utility.RandomString
import org.springframework.stereotype.Service

@Service
class RegisterService(private val customerRepository: CustomerRepository) {

    fun test(): MutableList<Customer> {
        customerRepository.save(
            Customer(
                vendorCode = RandomString.make(),
                suffix = CustomerSuffix.MR
            )
        )
        return customerRepository.findAll()
    }

}