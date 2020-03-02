package com.roadmountain.sim.config

import com.roadmountain.sim.auth.UserDetailsService
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: BCryptPasswordEncoder
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/api/admin/reset")
            .permitAll()

            .antMatchers("/api/admin/**")
            .hasAuthority("ADMIN")

            .and()
            .formLogin()
            .permitAll()

            .and()
            .logout()
            .permitAll()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder)
    }
}