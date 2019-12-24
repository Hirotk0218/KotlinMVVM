package com.example.kotlinmvvm.di

import com.example.kotlinmvvm.MainFragment
import com.example.kotlinmvvm.di.scope.MainScope
import dagger.Component

@Component(modules = [MainModule::class, DataModule::class])
@MainScope
interface MainComponent : FragmentInjector<MainFragment> {

    @Component.Builder
    interface Builder {
        fun build(): MainComponent
        fun dataModule(module: DataModule): Builder
        fun mainModule(module: MainModule): Builder
    }
}