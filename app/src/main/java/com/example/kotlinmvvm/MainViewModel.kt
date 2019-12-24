package com.example.kotlinmvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinmvvm.model.MainItem
import com.example.kotlinmvvm.repository.NumberRepository

class MainViewModel(val repository: NumberRepository) : ViewModel() {

    private var totalNumber = 0

    private val _items: MutableLiveData<List<MainItem>> =
            MutableLiveData<List<MainItem>>().also { live ->
                live.value = emptyList()
            }
    val items: LiveData<List<MainItem>>
        get() = _items

    fun load() {
        _items.value = emptyList()
        _items.postValue(repository.items())
    }

    fun addition(number : String) : String {
        totalNumber += number.toInt()
        return totalNumber.toString()
    }
}