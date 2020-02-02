package com.roadmountain.sim.configuration

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes
import com.roadmountain.sim.gmail.MongoDataStoreFactory
import com.roadmountain.sim.repository.GoogleCredentialRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.io.InputStreamReader


@Configuration
class GoogleApiConfiguration {
    @Value("classpath:config/credentials.json")
    private lateinit var credentialsFile: Resource

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
        val clientSecrets = GoogleClientSecrets.load(jsonFactory, InputStreamReader(credentialsFile.inputStream))
        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(
            httpTransport,
            jsonFactory,
            clientSecrets,
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