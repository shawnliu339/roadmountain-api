package com.roadmountain.sim.repository

import com.roadmountain.sim.domain.entity.OrderId
import org.springframework.data.mongodb.repository.MongoRepository

interface OrderIdRepository : MongoRepository<OrderId, String>