package com.roadmountain.sim.repository

import com.roadmountain.sim.domain.entity.Customer
import org.springframework.data.domain.Range
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDate

interface CustomerRepository : MongoRepository<Customer, String> {
    fun findByDateOfBirthBetween(range: Range<LocalDate>): List<Customer>
}