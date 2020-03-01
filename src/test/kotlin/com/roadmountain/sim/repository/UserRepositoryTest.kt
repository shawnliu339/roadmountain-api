package com.roadmountain.sim.repository

import com.roadmountain.sim.common.TestBase
import com.roadmountain.sim.domain.entity.User
import com.roadmountain.sim.domain.enum.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException


class UserRepositoryTest : TestBase() {
    @Autowired
    lateinit var target: UserRepository

    @Test
    fun `test findByEmail() and roles enum converter`() {
        val expected = target.save(
            User(
                email = "test@test.com",
                password = "test",
                enabled = true,
                roles = setOf(Role.ADMIN)
            )
        )

        val actual = target.findByEmail("test@test.com")

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `test email unique`() {
        val testCase = User(
            email = "test@test.com",
            password = "test",
            enabled = true,
            roles = setOf(Role.ADMIN)
        )
        target.save(testCase)

        assertThrows<DuplicateKeyException> { target.save(testCase.copy()) }
    }
}