package com.example.kotlinmvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinmvvm.model.MainItem

class MainItemViewModel {

    private val _number: MutableLiveData<String> = MutableLiveData()
    val number: LiveData<String>
        get() = _number

    private val item: MutableLiveData<MainItem> =
            MutableLiveData<MainItem>().also { mutableLiveData ->
                mutableLiveData.observeForever { item ->
                    _number.postValue(item.name)
                }
            }

    fun set(item: MainItem) {
        this.item.postValue(item)
    }
}