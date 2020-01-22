package com.roadmountain.sim.repository

import com.roadmountain.sim.model.Customer
import org.springframework.data.mongodb.repository.MongoRepository

interface CustomerRepository : MongoRepository<Customer, String>