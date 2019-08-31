package ru.egoncharovsky.revolut.backendtest

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

open class SimpleRepository<Entity : ru.egoncharovsky.revolut.backendtest.Entity> : Repository<Entity> {

    private val lastId: AtomicLong = AtomicLong()
    private val entities: MutableMap<Long, Entity> = ConcurrentHashMap()

    override fun save(entity: Entity): Entity {
        val id = entity.id ?: run {
            val id = lastId.getAndAdd(1)
            entity.id = id
            id
        }

        entities[id] = entity

        return entity
    }

    override fun get(id: Long): Entity? = entities[id]

    override fun delete(id: Long) {
        entities.remove(id)
    }
}