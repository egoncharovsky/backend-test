package ru.egoncharovsky.revolut.backendtest

abstract class CrudController<Entity : ru.egoncharovsky.revolut.backendtest.Entity>(
        private val repository: Repository<Entity>
) {

    fun create(entity: Entity): Entity = repository.save(entity)

    fun update(entity: Entity): Entity = repository.save(entity)

    fun delete(id: Long) = repository.delete(id)

    fun get(id: Long): Entity = repository.get(id)

    fun getAll(): Collection<Entity> = repository.getAll()
}