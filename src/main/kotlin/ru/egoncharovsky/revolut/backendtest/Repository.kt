package ru.egoncharovsky.revolut.backendtest

interface Repository<Entity : ru.egoncharovsky.revolut.backendtest.Entity> {

    fun save(entity: Entity): Entity

    fun get(id: Long): Entity?

    fun delete(id: Long)
}