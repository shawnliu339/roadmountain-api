package com.roadmountain.sim.repository

import com.roadmountain.sim.entity.GoogleCredential
import org.springframework.data.mongodb.repository.MongoRepository

interface GoogleCredentialRepository : MongoRepository<GoogleCredential, String> {
}