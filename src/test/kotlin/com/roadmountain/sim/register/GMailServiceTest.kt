package com.roadmountain.sim.register

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource

@SpringBootTest
class GMailServiceTest {
    @Value("classpath:config/credentials.json")
    private lateinit var file: Resource

    @Test
    fun test() {
        file.inputStream
        println(file)
    }

}