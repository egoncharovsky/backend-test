package ru.egoncharovsky.revolut.backendtest

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

abstract class CrudController<Entity : ru.egoncharovsky.revolut.backendtest.Entity>(
        private val repository: Repository<Entity>
) {

    @PostMapping
    @ApiOperation("Create new entity")
    fun create(@RequestBody entity: Entity): Entity = repository.save(entity)

    @PutMapping("/{id}")
    @ApiOperation("Update entity by id")
    fun update(@PathVariable id: Long, @RequestBody entity: Entity): Entity {
        require(repository.find(id) != null) { "Id $id is not existed" }
        require(id == entity.id) { "Entity id ${entity.id} doesn't corresponds to $id" }
        return repository.save(entity)
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete entity by id")
    fun delete(@PathVariable id: Long) = repository.delete(id)

    @DeleteMapping
    @ApiOperation("Delete all entities")
    fun deleteAll() = repository.deleteAll()

    @GetMapping("/{id}")
    @ApiOperation("Get entity by id")
    fun get(@PathVariable id: Long): Entity = repository.get(id)

    @GetMapping
    @ApiOperation("Get all entities")
    fun getAll(): Collection<Entity> = repository.getAll()
}