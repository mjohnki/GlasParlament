package de.glasparlament.agendaitem.di

import de.glasparlament.agendaItemRepository.AgendaItemRepository
import de.glasparlament.agendaitem.detail.AgendaItemDetailViewModel
import de.glasparlament.agendaitem.detail.AgendaItemDetailViewModelImpl
import de.glasparlament.agendaitem.detail.AgendaItemUseCase
import de.glasparlament.agendaitem.overview.AgendaItemListUseCase
import de.glasparlament.agendaitem.overview.AgendaItemViewModel
import de.glasparlament.agendaitem.overview.AgendaItemViewModelImpl
import de.glasparlament.agendaitem.search.AgendaItemSearchUseCase
import de.glasparlament.agendaitem.search.AgendaItemSearchViewModel
import de.glasparlament.agendaitem.search.AgendaItemSearchViewModelImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val agendaItemModule = module {
    factory<AgendaItemListUseCase> { provideAgendaItemListUseCase(get()) }
    factory<AgendaItemSearchUseCase> { provideAgendaItemSearchUseCase(get()) }
    factory<AgendaItemUseCase> { provideAgendaItemUseCase(get()) }
    viewModel<AgendaItemViewModel> { provideAgendaItemListViewModel(get()) }
    viewModel<AgendaItemDetailViewModel> { provideAgendaItemDetailViewModel(get()) }
    viewModel<AgendaItemSearchViewModel> { provideAgendaItemSearchViewModel(get()) }

}

fun provideAgendaItemListUseCase(repository: AgendaItemRepository) =
        AgendaItemListUseCase(repository)

fun provideAgendaItemSearchUseCase(repository: AgendaItemRepository) =
        AgendaItemSearchUseCase(repository)

fun provideAgendaItemUseCase(repository: AgendaItemRepository) =
        AgendaItemUseCase(repository)

fun provideAgendaItemListViewModel(useCase: AgendaItemListUseCase) =
        AgendaItemViewModelImpl(useCase)

fun provideAgendaItemDetailViewModel(useCase: AgendaItemUseCase) =
        AgendaItemDetailViewModelImpl(useCase)

fun provideAgendaItemSearchViewModel(useCase: AgendaItemSearchUseCase) =
        AgendaItemSearchViewModelImpl(useCase)
