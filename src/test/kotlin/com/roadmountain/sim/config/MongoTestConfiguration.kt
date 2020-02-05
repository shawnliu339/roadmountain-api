package com.roadmountain.sim.config

import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.core.MongoTemplate


class MongoTestConfiguration {
    companion object {
        private const val MONGO_DB_URL = "mongodb://localhost:27017/test"
    }

    @Bean
    fun mongoTemplate(): MongoTemplate {
        val client = MongoClients.create(MONGO_DB_URL)
        return MongoTemplate(client, "test")
    }

    @Bean
    fun dbFactory(mongoTemplate: MongoTemplate): MongoDbFactory {
        return mongoTemplate.mongoDbFactory
    }

    @Bean
    fun transactionManager(dbFactory: MongoDbFactory): MongoTransactionManager {
        return MongoTransactionManager(dbFactory)
    }

}