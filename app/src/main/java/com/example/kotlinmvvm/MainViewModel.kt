package com.example.kotlinmvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinmvvm.repository.NumberRepository
import java.util.*

class MainViewModel(private val repository: NumberRepository) : ViewModel() {

    private val itemList = ArrayList<Int>()
    private val _items: MutableLiveData<List<Int>> =
        MutableLiveData<List<Int>>().also { live ->
            live.value = emptyList()
        }

    val items: LiveData<List<Int>>
        get() = _items

    fun load() {
        _items.value = emptyList()
        itemList.clear()
        itemList.addAll(repository.items())
        _items.postValue(itemList)
    }

    fun loadMore(page: Int = 1) {
        if (page > 1) {
            itemList.addAll(repository.items().map { it + 10 * (page - 1) })
            _items.value = itemList
        }
    }
}