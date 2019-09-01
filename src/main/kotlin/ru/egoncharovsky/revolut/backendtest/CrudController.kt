package ru.egoncharovsky.revolut.backendtest

import org.springframework.web.bind.annotation.*

abstract class CrudController<Entity : ru.egoncharovsky.revolut.backendtest.Entity>(
        private val repository: Repository<Entity>
) {

    @PostMapping
    fun create(@RequestBody entity: Entity): Entity = repository.save(entity)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody entity: Entity): Entity {
        require(repository.find(id) != null) {
            "Id $id is not existed"
        }
        return repository.save(entity)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = repository.delete(id)

    @DeleteMapping
    fun deleteAll() = repository.deleteAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Entity = repository.get(id)

    @GetMapping
    fun getAll(): Collection<Entity> = repository.getAll()
}