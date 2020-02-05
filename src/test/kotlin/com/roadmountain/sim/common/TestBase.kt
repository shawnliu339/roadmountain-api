package com.roadmountain.sim.common

import com.roadmountain.sim.config.MongoTestConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.transaction.annotation.Transactional

@DataMongoTest
@ImportAutoConfiguration(classes = [MongoTestConfiguration::class])
@Transactional
class TestBase {
}