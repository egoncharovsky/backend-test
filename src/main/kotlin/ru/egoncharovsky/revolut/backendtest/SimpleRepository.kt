package ru.egoncharovsky.revolut.backendtest

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import kotlin.reflect.KClass

abstract class SimpleRepository<Entity : ru.egoncharovsky.revolut.backendtest.Entity>(
        private val entityClass: KClass<Entity>
) : Repository<Entity> {

    protected val lastId: AtomicLong = AtomicLong(1)
    protected val entities: MutableMap<Long, Entity> = ConcurrentHashMap()

    override fun save(entity: Entity): Entity {
        val id = entity.id ?: run {
            val id = lastId.getAndAdd(1)
            entity.id = id
            id
        }

        entities[id] = entity

        return entity
    }

    override fun get(id: Long): Entity =
            entities[id] ?: throw IllegalArgumentException("$entityClass with id $id does not exist")

    override fun find(id: Long): Entity? = entities[id]

    override fun delete(id: Long) {
        entities.remove(id)
    }

    override fun getAll(): Collection<Entity> = entities.values

    override fun deleteAll() = entities.clear()
}