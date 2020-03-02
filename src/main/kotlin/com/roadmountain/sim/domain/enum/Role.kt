package com.roadmountain.sim.domain.enum

import org.springframework.security.core.GrantedAuthority

enum class Role(value: String) : GrantedAuthority {
    ADMIN("ADMIN");

    override fun getAuthority(): String {
        return this.name
    }
}