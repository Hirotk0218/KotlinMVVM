package com.example.kotlinmvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinmvvm.model.MainItem
import com.example.kotlinmvvm.repository.NumberRepository

class MainViewModel(val repository: NumberRepository) : ViewModel() {

    val pokemonList = ArrayList<MainItem>()
    val refinePokemonList = ArrayList<MainItem>()

    private val _items: MutableLiveData<List<MainItem>> =
            MutableLiveData<List<MainItem>>().also { live ->
                live.value = emptyList()
            }
    val items: LiveData<List<MainItem>>
        get() = _items

    val searchText: MutableLiveData<String> =
        MutableLiveData<String>().also { live ->
            live.value = ""
        }

    fun load() {
        _items.value = emptyList()
        pokemonList.addAll(repository.items())
        _items.value = pokemonList
    }

    fun searchTargetList(name: String) {
        _items.value = emptyList()
        refinePokemonList.clear()
        // 部分一致
        val regex = Regex(name)
        val items = pokemonList.filter {item -> regex.containsMatchIn(item.name)}
        // 完全一致
        //val items = pokemonList.filter {item -> item.name == name}
        refinePokemonList.addAll(items)
        _items.value = refinePokemonList
    }

    fun clearSearchText() {
        searchText.value = ""
        _items.value = emptyList()
        _items.value = pokemonList
    }
}