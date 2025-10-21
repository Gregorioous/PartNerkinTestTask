package org.nerkin.project.data.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.nerkin.project.CalendarViewModel

val androidModule = module {

    /*// HttpClient (ktor)
    single { provideHttpClient() }

    // API клиент
    single { ApiClient(get()) }*/

    // Оптимизированный ImageLoader с кэшированием
    /* single {
         ImageLoader.Builder(androidContext())
             .crossfade(true)
             .memoryCache {
                 MemoryCache.Builder(androidContext())
                     .maxSizePercent(0.25)
                     .build()
             }
             .diskCachePolicy(CachePolicy.ENABLED)
             .respectCacheHeaders(false)
             .components {
                 add(SvgDecoder.Factory())
             }
             .build()
     }*/

    // ViewModel
    viewModel { CalendarViewModel(get()) }
}