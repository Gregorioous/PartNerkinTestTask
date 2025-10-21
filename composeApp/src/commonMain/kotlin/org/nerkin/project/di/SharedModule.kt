package org.nerkin.project.di


import org.koin.dsl.module
import org.nerkin.project.data.repository.ListRepositoryImpl
import org.nerkin.project.domain.interactor.CalendarInteractor
import org.nerkin.project.domain.interactor.CalendarInteractorImpl
import org.nerkin.project.domain.repository.ListRepository
import org.nerkin.project.domain.usecase.GetListUseCase
import org.nerkin.project.domain.usecase.GetViewUseCase


val sharedModule = module {

    // Репозиторий
    single<ListRepository> { ListRepositoryImpl(get()) }

    // Юзкейсы
    single { GetListUseCase(get()) }
    single { GetViewUseCase(get()) }

    // Интерактор — основная точка входа во ViewModel
    single<CalendarInteractor> { CalendarInteractorImpl(get(), get()) }
}