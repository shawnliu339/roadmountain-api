package com.roadmountain.sim.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfiguration : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/static/**")
            .addResourceLocations(
                "classpath:/static/front/dist/static/",
                "classpath:/static/front/dist/static/css/",
                "classpath:/static/front/dist/static/js/"
            )
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "https://roadmountain-activate.herokuapp.com",
                "http://localhost:8081",
                "https://localhost:8080",
                "http://localhost:8080"
            )
            .allowedMethods(CorsConfiguration.ALL)
            .allowCredentials(false)
            .maxAge(3600)
    }
}