package de.glasparlament.search.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.glasparlament.agendaItemRepository.AgendaItemRepository
import de.glasparlament.search.ui.SearchFragment
import de.glasparlament.search.useCase.SearchUseCase
import de.glasparlament.search.vm.SearchViewModelFactory

@Module(includes = [SearchModule.Binding::class])
class SearchModule {

    @Provides
    fun provideAgendaItemSearchUseCase(repository: AgendaItemRepository) =
            SearchUseCase(repository)

    @Provides
    fun provideAgendaItemSearchViewModelFactory(useCase: SearchUseCase) =
            SearchViewModelFactory(useCase)

    @Module
    abstract class Binding {

        @ContributesAndroidInjector
        abstract fun contributeSearchFragment(): SearchFragment
    }
}
