package com.codingwithmitch.daggerhiltplayground.di

import com.codingwithmitch.daggerhiltplayground.model.Blog
import com.codingwithmitch.daggerhiltplayground.retrofit.BlogNetworkEntity
import com.codingwithmitch.daggerhiltplayground.retrofit.BlogRetrofit
import com.codingwithmitch.daggerhiltplayground.retrofit.NetworkMapper
import com.codingwithmitch.daggerhiltplayground.util.EntityMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//colocar como object para que seja um singleton e que as dependencias fiquem estáticas
@Module
@InstallIn(ApplicationComponent::class)
//usar instalLIn para informar onde iremos intala-lo
object RetrofitModule {

    //transformar o Gson para Kotlin
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    //construir o retrofit
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://open-api.xyz/placeholder/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    //criando a instancia do retrofit
    @Singleton
    @Provides
    fun providesBlogService(retrofit: Retrofit.Builder): BlogRetrofit{
        return retrofit
            .build()
            .create(BlogRetrofit::class.java)
    }

}