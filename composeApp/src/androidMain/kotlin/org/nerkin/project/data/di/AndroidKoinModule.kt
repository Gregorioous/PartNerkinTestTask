package org.nerkin.project.data.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.nerkin.project.CalendarViewModel
import org.nerkin.project.data.api.ApiClient
import org.nerkin.project.data.remote.DefaultHttpClientProvider
import org.nerkin.project.data.remote.HttpClientProvider

// androidModule.kt
val androidModule = module {
    // HttpClientProvider
    single<HttpClientProvider> { DefaultHttpClientProvider() }

    // HttpClient (через провайдер)
    single { get<HttpClientProvider>().provideHttpClient() }

    // API клиент
    single { ApiClient(get()) }

    // ViewModel
    viewModel { CalendarViewModel(get()) }
}