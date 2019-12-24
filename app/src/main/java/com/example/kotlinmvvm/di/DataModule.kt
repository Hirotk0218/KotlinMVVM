package com.example.kotlinmvvm.di

import com.example.kotlinmvvm.repository.NumberRepository
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class DataModule {
    @Provides
    @Inject
    fun provideNumberRepository(): NumberRepository {
        return NumberRepository()
    }
}