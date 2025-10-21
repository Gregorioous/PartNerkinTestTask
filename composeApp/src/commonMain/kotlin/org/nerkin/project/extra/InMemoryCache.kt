package org.nerkin.project.extra

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class InMemoryCache<K, V> {
    private val map = mutableMapOf<K, V>()
    private val lock = Mutex()

    suspend fun getOrPut(key: K, provider: suspend () -> V): V {
        return lock.withLock {
            map[key] ?: provider().also { map[key] = it }
        }
    }

    suspend fun get(key: K): V? = lock.withLock { map[key] }

    suspend fun put(key: K, value: V) = lock.withLock { map[key] = value }

    suspend fun clear() = lock.withLock { map.clear() }
}
