package ru.egoncharovsky.revolut.backendtest

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping

abstract class CrudController<Entity : ru.egoncharovsky.revolut.backendtest.Entity>(
        private val repository: Repository<Entity>
) {

    @PostMapping
    fun create(entity: Entity): Entity = repository.save(entity)

    @PutMapping
    fun update(entity: Entity): Entity = repository.save(entity)

    @DeleteMapping
    fun delete(id: Long) = repository.delete(id)

    @DeleteMapping
    fun deleteAll() = repository.deleteAll()

    @GetMapping
    fun get(id: Long): Entity = repository.get(id)

    @GetMapping
    fun getAll(): Collection<Entity> = repository.getAll()
}