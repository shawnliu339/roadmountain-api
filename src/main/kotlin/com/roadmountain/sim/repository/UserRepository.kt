package com.roadmountain.sim.repository

import com.roadmountain.sim.domain.entity.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String> {
    fun findByEmail(email: String): User?
}