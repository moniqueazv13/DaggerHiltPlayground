package com.codingwithmitch.daggerhiltplayground.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.codingwithmitch.daggerhiltplayground.model.Blog
import com.codingwithmitch.daggerhiltplayground.repository.MainRepository
import com.codingwithmitch.daggerhiltplayground.util.DataState
import dagger.assisted.Assisted
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MainViewModel
@ViewModelInject constructor(
    private val mainRepository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<Blog>>>
        get() = _dataState

    //passando os estados para a viewmodel
    fun setStateEvent(mainStateEvent: MainStateEvent) = viewModelScope.launch {
        when (mainStateEvent) {
            is MainStateEvent.GetBlogEvents -> {
                mainRepository.getBlog()
                    .onEach { dataState ->
                        _dataState.value = dataState
                    }
                    .launchIn(viewModelScope)
            }
            is MainStateEvent.None ->{
                //nothing
            }
        }
    }

}

sealed class MainStateEvent {
    object GetBlogEvents : MainStateEvent()
    object None : MainStateEvent()
}