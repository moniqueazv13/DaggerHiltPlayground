package com.codingwithmitch.daggerhiltplayground.repository

import com.codingwithmitch.daggerhiltplayground.model.Blog
import com.codingwithmitch.daggerhiltplayground.retrofit.BlogRetrofit
import com.codingwithmitch.daggerhiltplayground.retrofit.NetworkMapper
import com.codingwithmitch.daggerhiltplayground.room.BlogDao
import com.codingwithmitch.daggerhiltplayground.room.CacheMapper
import com.codingwithmitch.daggerhiltplayground.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository
constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun getBlog(): Flow<DataState<List<Blog>>> = flow {
        // essa função irá pegar uma lista de blogs da API
        emit(DataState.Loading)// pra mostrar a progressbar
        delay(1000)// para enxergar melhor a progressbar
        try {
            val networkBlogs = blogRetrofit.get()//recuperar os dados em qualquer thread
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for (blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntitiy(blog))
            }
            val cachedBlogs =  blogDao.get()
            //para emitir esses dados ao usuário
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }
}