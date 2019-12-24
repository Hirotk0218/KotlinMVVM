package com.example.kotlinmvvm.di

import androidx.lifecycle.ViewModelProviders
import com.example.kotlinmvvm.MainFragment
import com.example.kotlinmvvm.MainViewModel
import com.example.kotlinmvvm.MainViewModelFactory
import com.example.kotlinmvvm.repository.NumberRepository
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class MainModule(private val fragment: MainFragment) {

    @Inject
    @Provides
    fun provideMainViewModel(repository: NumberRepository): MainViewModel {
        return ViewModelProviders.of(fragment, MainViewModelFactory(repository))
            .get(MainViewModel::class.java)
    }
}