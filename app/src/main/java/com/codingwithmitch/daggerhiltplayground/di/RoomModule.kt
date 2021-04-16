package com.codingwithmitch.daggerhiltplayground.di

import android.content.Context
import androidx.room.Room
import com.codingwithmitch.daggerhiltplayground.room.BlogDao
import com.codingwithmitch.daggerhiltplayground.room.BlogDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {
    //para o Hilt ter acesso ao banco
    @Singleton
    @Provides
    fun provideBlogDb(@ApplicationContext context: Context): BlogDatabase {
        return Room.databaseBuilder(
            context,
            BlogDatabase::class.java,
            BlogDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()//permite o room destruir e criar tabelas para banco
            .build()

    }

    //para o dao dar acesso a todas as funções que podemos usar para interagir com o banco
    @Singleton
    @Provides
    fun provideBlogDAO(blogDatabase: BlogDatabase): BlogDao {
        return blogDatabase.blogDao()
    }

}