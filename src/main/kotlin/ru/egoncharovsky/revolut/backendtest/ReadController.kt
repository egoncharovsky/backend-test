package ru.egoncharovsky.revolut.backendtest

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

abstract class ReadController<Entity : ru.egoncharovsky.revolut.backendtest.Entity>(
        private val repository: Repository<Entity>
) {

    @GetMapping("/{id}")
    @ApiOperation("Get entity by id")
    fun get(@PathVariable id: Long): Entity = repository.get(id)

    @GetMapping
    @ApiOperation("Get all entities")
    fun getAll(): Collection<Entity> = repository.getAll()
}