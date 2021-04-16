package com.codingwithmitch.daggerhiltplayground.util

interface EntityMapper<Entity, DomainModel> {
    fun mapFromEntity(entity: Entity): DomainModel
    fun mapToEntitiy(domainModel: DomainModel) : Entity
}