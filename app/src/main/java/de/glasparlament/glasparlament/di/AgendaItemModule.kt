package de.glasparlament.glasparlament.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.glasparlament.agendaitem.application.detail.AgendaItemDetailViewModelFactory
import de.glasparlament.agendaitem.application.overview.AgendaItemFragment
import de.glasparlament.agendaitem.application.overview.AgendaItemViewModelFactory
import de.glasparlament.agendaitem_repository.AgendaItemEndpoint
import de.glasparlament.agendaitem.domain.AgendaItemListUseCase
import de.glasparlament.agendaitem.domain.AgendaItemUseCase
import de.glasparlament.agendaitem_repository.AgendaItemApi
import de.glasparlament.agendaitem_repository.AgendaItemRepository
import de.glasparlament.agendaitem_repository.AgendaItemRepositoryImpl
import de.glasparlament.meeting_repository.MeetingRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [AgendaItemModule.Binding::class])
class AgendaItemModule {

    /** Api */
    @Provides
    @Singleton
    fun provideAgendaItemApi(endpoint: AgendaItemEndpoint): AgendaItemApi {
        return AgendaItemApi(endpoint)
    }

    /** Endpoint */
    @Provides
    @Singleton
    fun provideAgendaItemEndpoint(retrofit: Retrofit): AgendaItemEndpoint {
        return retrofit
                .create(AgendaItemEndpoint::class.java)
    }

    /** Repository*/
    @Provides
    @Singleton
    fun provideAgendaItemRepository(agendaItemApi: AgendaItemApi): AgendaItemRepository {
        return AgendaItemRepositoryImpl(agendaItemApi)
    }

    /** UseCase*/
    @Provides
    @Singleton
    fun provideAgendaItemListUseCase(repository: MeetingRepository): AgendaItemListUseCase {
        return AgendaItemListUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAgendaItemUseCase(repository: AgendaItemRepository): AgendaItemUseCase {
        return AgendaItemUseCase(repository)
    }

    /** ViewModelFactory*/
    @Provides
    @Singleton
    fun provideAgendaItemListViewModelFactory(useCase: AgendaItemListUseCase): AgendaItemViewModelFactory {
        return AgendaItemViewModelFactory(useCase)
    }

    @Provides
    @Singleton
    fun provideAgendaItemDetailViewModelFactory(useCase: AgendaItemUseCase): AgendaItemDetailViewModelFactory {
        return AgendaItemDetailViewModelFactory(useCase)
    }


    @Module
    abstract class Binding {

        @ContributesAndroidInjector
        abstract fun contributeAgendaItemListFragment(): AgendaItemFragment

        @ContributesAndroidInjector
        abstract fun contributeAgendaItemDetailFragment(): de.glasparlament.agendaitem.application.detail.AgendaItemDetailFragment
    }
}