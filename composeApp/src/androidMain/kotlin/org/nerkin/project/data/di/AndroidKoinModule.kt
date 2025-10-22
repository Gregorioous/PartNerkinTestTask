package org.nerkin.project.data.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.nerkin.project.CalendarViewModel
import org.nerkin.project.data.remote.ApiClient
import org.nerkin.project.data.remote.provideHttpClient

val androidModule = module {

    // HttpClient (ktor)
    single { provideHttpClient() }

    // API клиент
    single { ApiClient(get()) }

    // ViewModel
    viewModel { CalendarViewModel(get()) }
}