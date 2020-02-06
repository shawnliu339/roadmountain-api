package com.roadmountain.sim.gmail

import com.google.api.client.util.store.AbstractDataStoreFactory
import com.roadmountain.sim.domain.entity.GoogleCredential
import com.roadmountain.sim.repository.GoogleCredentialRepository
import java.io.Serializable

class MongoDataStoreFactory(
    private val repository: GoogleCredentialRepository
) : AbstractDataStoreFactory() {

    override fun <V : Serializable> createDataStore(id: String): MongoDataStore<V> {
        return MongoDataStore(this, id, repository)
    }

    class MongoDataStore<V : Serializable>(
        dataStore: MongoDataStoreFactory,
        key: String,
        private val repository: GoogleCredentialRepository
    ) : AbstractMongoDataStore<V>(dataStore, key) {
        init {
            credential = repository.findAll().getOrElse(0) { GoogleCredential() }
            save()
        }

        override fun save() {
            credential = repository.save(credential)
        }
    }
}