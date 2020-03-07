package de.glasparlament.agendaitem.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.glasparlament.agendaitem.detail.AgendaItemDetailFragment
import de.glasparlament.agendaitem.detail.AgendaItemDetailViewModelFactory
import de.glasparlament.agendaitem.detail.AgendaItemUseCase
import de.glasparlament.agendaitem.overview.AgendaItemFragment
import de.glasparlament.agendaitem.overview.AgendaItemListUseCase
import de.glasparlament.agendaitem.overview.AgendaItemViewModelFactory
import de.glasparlament.repository.agendaItem.AgendaItemRepository

@Module(includes = [AgendaItemModule.Binding::class])
class AgendaItemModule {

    @Provides
    fun provideAgendaItemListUseCase(repository: AgendaItemRepository) =
            AgendaItemListUseCase(repository)

    @Provides
    fun provideAgendaItemUseCase(repository: AgendaItemRepository) =
            AgendaItemUseCase(repository)

    @Provides
    fun provideAgendaItemViewModelFactory(useCase: AgendaItemListUseCase) =
            AgendaItemViewModelFactory(useCase)

    @Provides
    fun provideAgendaItemDetailViewModelFactory(useCase: AgendaItemUseCase) =
            AgendaItemDetailViewModelFactory(useCase)

    @Module
    abstract class Binding {

        @ContributesAndroidInjector
        abstract fun contributeAgendaItemListFragment(): AgendaItemFragment

        @ContributesAndroidInjector
        abstract fun contributeAgendaItemDetailFragment(): AgendaItemDetailFragment
    }
}
