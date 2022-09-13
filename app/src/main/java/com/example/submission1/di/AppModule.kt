package com.example.submission1.di

import com.example.submission1.core.domain.usecase.AnimeInteractor
import com.example.submission1.core.domain.usecase.AnimeUseCase
import com.example.submission1.detail.DetailViewModel
import com.example.submission1.discover.DiscoverViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<AnimeUseCase> { AnimeInteractor(get()) }
}

val viewModelModule = module {
    viewModel { DiscoverViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}