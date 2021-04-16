package com.codingwithmitch.daggerhiltplayground.util

import androidx.room.Database
import java.lang.Exception

sealed class DataState<out R> {
    // classe para trazer alertas, mensagens
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}