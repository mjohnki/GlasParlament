package de.glasparlament.agendaitem.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.glasparlament.agendaItemRepository.AgendaItemRepository
import de.glasparlament.agendaitem.detail.AgendaItemDetailFragment
import de.glasparlament.agendaitem.detail.AgendaItemDetailViewModelFactory
import de.glasparlament.agendaitem.detail.AgendaItemDetailViewModelImpl
import de.glasparlament.agendaitem.detail.AgendaItemUseCase
import de.glasparlament.agendaitem.overview.AgendaItemFragment
import de.glasparlament.agendaitem.overview.AgendaItemListUseCase
import de.glasparlament.agendaitem.overview.AgendaItemViewModelFactory
import de.glasparlament.agendaitem.overview.AgendaItemViewModelImpl
import de.glasparlament.agendaitem.search.AgendaItemSearchFragment
import de.glasparlament.agendaitem.search.AgendaItemSearchUseCase
import de.glasparlament.agendaitem.search.AgendaItemSearchViewModelFactory
import de.glasparlament.agendaitem.search.AgendaItemSearchViewModelImpl

@Module(includes = [AgendaItemModule.Binding::class])
class AgendaItemModule {

    @Provides
    fun provideAgendaItemListUseCase(repository: AgendaItemRepository) =
            AgendaItemListUseCase(repository)

    @Provides
    fun provideAgendaItemSearchUseCase(repository: AgendaItemRepository) =
            AgendaItemSearchUseCase(repository)

    @Provides
    fun provideAgendaItemUseCase(repository: AgendaItemRepository) =
            AgendaItemUseCase(repository)

    @Provides
    fun provideAgendaItemViewModelFactory(useCase: AgendaItemListUseCase) =
            AgendaItemViewModelFactory(useCase)

    @Provides
    fun provideAgendaItemDetailViewModelFactory(useCase: AgendaItemUseCase) =
            AgendaItemDetailViewModelFactory(useCase)

    @Provides
    fun provideAgendaItemSearchViewModelFactory(useCase: AgendaItemSearchUseCase) =
            AgendaItemSearchViewModelFactory(useCase)

    @Module
    abstract class Binding {

        @ContributesAndroidInjector
        abstract fun contributeAgendaItemListFragment(): AgendaItemFragment

        @ContributesAndroidInjector
        abstract fun contributeAgendaItemSearchFragment(): AgendaItemSearchFragment

        @ContributesAndroidInjector
        abstract fun contributeAgendaItemDetailFragment(): AgendaItemDetailFragment
    }
}
