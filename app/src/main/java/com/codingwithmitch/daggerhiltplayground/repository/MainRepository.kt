package com.codingwithmitch.daggerhiltplayground.repository

import com.codingwithmitch.daggerhiltplayground.model.Blog
import com.codingwithmitch.daggerhiltplayground.util.DataState
import com.codingwithmitch.daggerhiltplayground.room.BlogDao
import com.codingwithmitch.daggerhiltplayground.retrofit.BlogRetrofit
import com.codingwithmitch.daggerhiltplayground.retrofit.NetworkMapper
import com.codingwithmitch.daggerhiltplayground.room.CacheMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MainRepository
constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun getBlogs(): Flow<DataState<List<Blog>>> = flow {

        //emite um estado de loading
        emit(DataState.Loading)
        delay(1000)
        try {
            // buscar os dados na Api
            val networkBlogs = blogRetrofit.get()

            //mapear os dados da api para o room
            val blogs = networkMapper.mapFromEntityList(networkBlogs)

            //para cada dado que ele buscou na api, ele salva no banco
            for (blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }

            //buscar todos os blogs salvos no banco
            val cachedBlogs = blogDao.get()

            //emite um estado de sucesso com todos os blogs buscados no banco
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        } catch (e: Exception) {

            //emite um estado de erro, caso ocorra alguma falha em algum processo acima
            emit(DataState.Error(e))
        }
    }
}