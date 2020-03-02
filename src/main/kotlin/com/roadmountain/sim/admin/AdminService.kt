package com.roadmountain.sim.admin

import com.roadmountain.sim.domain.entity.User
import com.roadmountain.sim.domain.enum.Role
import com.roadmountain.sim.repository.UserRepository
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
@ConfigurationProperties(prefix = "admin")
class AdminService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    lateinit var email: String
    lateinit var password: String

    fun reset() {
        userRepository.findByEmail(email) ?: userRepository.save(
            User(
                email = email,
                password = passwordEncoder.encode(password),
                enabled = true,
                roles = setOf(Role.ADMIN)
            )
        )
    }
}