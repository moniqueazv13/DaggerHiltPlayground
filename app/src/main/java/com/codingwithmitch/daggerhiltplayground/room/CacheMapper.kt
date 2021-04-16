package com.codingwithmitch.daggerhiltplayground.room

import com.codingwithmitch.daggerhiltplayground.model.Blog
import com.codingwithmitch.daggerhiltplayground.retrofit.BlogNetworkEntity
import com.codingwithmitch.daggerhiltplayground.util.EntityMapper
import javax.inject.Inject

class CacheMapper
@Inject constructor() :
    EntityMapper<BlogCacheEntity, Blog> {
    override fun mapFromEntity(entity: BlogCacheEntity): Blog {
        return Blog(
            id = entity.id,
            title = entity.title,
            body = entity.body,
            category = entity.category,
            image = entity.image
        )
    }

    override fun mapToEntitiy(domainModel: Blog): BlogCacheEntity {
        return BlogCacheEntity(
            id = domainModel.id,
            title = domainModel.title,
            body = domainModel.body,
            category = domainModel.category,
            image = domainModel.image
        )
    }

    fun mapFromEntityList(entitie: List<BlogCacheEntity>): List<Blog> {
        return entitie.map { mapFromEntity(it) }
    }
}