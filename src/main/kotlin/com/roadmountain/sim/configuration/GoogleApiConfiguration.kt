package com.roadmountain.sim.configuration

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes
import com.roadmountain.sim.gmail.MongoDataStoreFactory
import com.roadmountain.sim.repository.GoogleCredentialRepository
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "google")
class GoogleApiConfiguration {
    lateinit var clientId: String
    lateinit var clientSecret: String

    @Bean
    fun httpTransport(): NetHttpTransport {
        return GoogleNetHttpTransport.newTrustedTransport()
    }

    @Bean
    fun jsonFactory(): JsonFactory {
        return JacksonFactory.getDefaultInstance()
    }

    @Bean
    fun mongoDataStoreFactory(repository: GoogleCredentialRepository): MongoDataStoreFactory {
        return MongoDataStoreFactory(repository)
    }

    /**
     * Creates an authorized Credential object.
     */
    @Bean
    fun credentials(
        httpTransport: NetHttpTransport,
        jsonFactory: JsonFactory,
        mongoDataStoreFactory: MongoDataStoreFactory
    ): Credential { // Load client secrets.
        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(
            httpTransport,
            jsonFactory,
            clientId,
            clientSecret,
            listOf(GmailScopes.GMAIL_SEND)
        ).setAccessType("offline")
            .setDataStoreFactory(mongoDataStoreFactory)
            .build()
        return AuthorizationCodeInstalledApp(flow, LocalServerReceiver()).authorize("user")
    }


    @Bean
    fun gmail(
        httpTransport: NetHttpTransport,
        jsonFactory: JsonFactory,
        credential: Credential
    ): Gmail {
        return Gmail.Builder(httpTransport, jsonFactory, credential)
            .setApplicationName("customer-service-mail")
            .build()
    }
}