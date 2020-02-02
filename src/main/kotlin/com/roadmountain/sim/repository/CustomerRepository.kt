package com.roadmountain.sim.repository

import com.roadmountain.sim.domain.entity.Customer
import org.springframework.data.mongodb.repository.MongoRepository

interface CustomerRepository : MongoRepository<Customer, String>