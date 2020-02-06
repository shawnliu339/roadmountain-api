package com.roadmountain.sim.repository

import com.roadmountain.sim.domain.entity.Customer
import org.springframework.data.domain.Range
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Instant

interface CustomerRepository : MongoRepository<Customer, String> {
    fun findByCreatedBetween(range: Range<Instant>): List<Customer>
}