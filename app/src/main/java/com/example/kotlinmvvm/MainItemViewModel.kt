package com.example.kotlinmvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainItemViewModel {

    private val _number: MutableLiveData<String> = MutableLiveData()
    val number: LiveData<String>
        get() = _number

    private val item: MutableLiveData<Int> =
            MutableLiveData<Int>().also { mutableLiveData ->
                mutableLiveData.observeForever { item ->
                    _number.postValue(item.toString())
                }
            }

    fun set(item: Int) {
        this.item.postValue(item)
    }
}