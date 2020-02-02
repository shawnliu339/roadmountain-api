package com.roadmountain.sim.gmail

import com.google.api.client.util.IOUtils
import com.google.api.client.util.Lists
import com.google.api.client.util.Preconditions
import com.google.api.client.util.store.AbstractDataStore
import com.google.api.client.util.store.DataStore
import com.google.api.client.util.store.DataStoreFactory
import com.google.api.client.util.store.DataStoreUtils
import com.roadmountain.sim.domain.entity.GoogleCredential
import java.io.IOException
import java.io.Serializable
import java.util.*
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * Abstract, thread-safe, in-memory implementation of a data store factory.
 */
open class AbstractMongoDataStore<V : Serializable?>(
    dataStoreFactory: DataStoreFactory,
    id: String
) : AbstractDataStore<V?>(dataStoreFactory, id) {
    /** Lock on access to the store.  */
    private val lock: Lock = ReentrantLock()
    /** Data store map from the key to the value.  */
    var credential: GoogleCredential = GoogleCredential()

    @Throws(IOException::class)
    override fun keySet(): Set<String> {
        lock.lock()
        return try {
            Collections.unmodifiableSet(credential.keyValueMap.keys)
        } finally {
            lock.unlock()
        }
    }

    @Throws(IOException::class)
    override fun values(): Collection<V> {
        lock.lock()
        return try {
            val result: MutableList<V> = Lists.newArrayList()
            for (bytes in credential.keyValueMap.values) {
                result.add(IOUtils.deserialize<V>(bytes))
            }
            Collections.unmodifiableList(result)
        } finally {
            lock.unlock()
        }
    }

    @Throws(IOException::class)
    override fun get(key: String?): V? {
        if (key == null) {
            return null
        }
        lock.lock()
        return try {
            IOUtils.deserialize<V>(credential.keyValueMap[key])
        } finally {
            lock.unlock()
        }
    }

    @Throws(IOException::class)
    override fun set(key: String?, value: V?): DataStore<V?>? {
        Preconditions.checkNotNull(key)
        Preconditions.checkNotNull(value)
        lock.lock()
        try {
            credential.keyValueMap[key!!] = IOUtils.serialize(value)
            save()
        } finally {
            lock.unlock()
        }
        return this
    }

    @Throws(IOException::class)
    override fun delete(key: String?): DataStore<V?> {
        if (key == null) {
            return this
        }
        lock.lock()
        try {
            credential.keyValueMap.remove(key)
            save()
        } finally {
            lock.unlock()
        }
        return this
    }

    @Throws(IOException::class)
    override fun clear(): DataStore<V?> {
        lock.lock()
        try {
            credential.keyValueMap.clear()
            // Do not need delete db, only update new empty hash map into db.
            save()
        } finally {
            lock.unlock()
        }
        return this
    }

    @Throws(IOException::class)
    override fun containsKey(key: String?): Boolean {
        if (key == null) {
            return false
        }
        lock.lock()
        return try {
            credential.keyValueMap.containsKey(key)
        } finally {
            lock.unlock()
        }
    }

    @Throws(IOException::class)
    override fun containsValue(value: V?): Boolean {
        if (value == null) {
            return false
        }
        lock.lock()
        return try {
            val serialized = IOUtils.serialize(value)
            for (bytes in credential.keyValueMap.values) {
                if (Arrays.equals(serialized, bytes)) {
                    return true
                }
            }
            false
        } finally {
            lock.unlock()
        }
    }

    @Throws(IOException::class)
    override fun isEmpty(): Boolean {
        lock.lock()
        return try {
            credential.keyValueMap.isEmpty()
        } finally {
            lock.unlock()
        }
    }

    @Throws(IOException::class)
    override fun size(): Int {
        lock.lock()
        return try {
            credential.keyValueMap.size
        } finally {
            lock.unlock()
        }
    }

    /**
     * Persist the key-value map into storage at the end of [.set], [.delete], and
     * [.clear].
     */
    @Throws(IOException::class)
    open fun save() {
    }

    override fun toString(): String {
        return DataStoreUtils.toString(this)
    }
}
