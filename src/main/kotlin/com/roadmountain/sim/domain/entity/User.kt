package com.roadmountain.sim.domain.entity

import com.roadmountain.sim.domain.enum.Role
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
    @Indexed(unique = true)
    val email: String,
    val password: String,
    val enabled: Boolean,
    val roles: Set<Role>
)