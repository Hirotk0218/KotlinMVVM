package com.example.kotlinmvvm.di

import androidx.fragment.app.Fragment
import com.example.kotlinmvvm.MainFragment

fun MainFragment.inject() {
    DaggerMainComponent.builder()
        .dataModule(DataModule())
        .mainModule(MainModule(this))
        .build()
        .inject(this)
}

interface Injector<T> {
    fun inject(sink: T)
}

interface FragmentInjector<T : Fragment> : Injector<T>