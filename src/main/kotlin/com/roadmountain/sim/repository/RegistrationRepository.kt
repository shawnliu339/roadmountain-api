package com.roadmountain.sim.repository

import com.roadmountain.sim.domain.entity.Registration
import org.springframework.data.domain.Range
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Instant

interface RegistrationRepository : MongoRepository<Registration, String> {
    fun findByCreatedBetween(range: Range<Instant>): List<Registration>

    fun findBySimNo(simNo: String): Registration?

    fun findByOrderId(orderId: String): Registration?
}