package de.glasparlament.glasparlament.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.glasparlament.glasparlament.agendaItemDetail.application.AgendaItemDetailFragment
import de.glasparlament.glasparlament.agendaItemDetail.application.AgendaItemDetailViewModelFactory
import de.glasparlament.glasparlament.agendaItemDetail.data.AgendaItemApi
import de.glasparlament.glasparlament.agendaItemDetail.data.AgendaItemDetailRepositoryImpl
import de.glasparlament.glasparlament.agendaItemDetail.data.AgendaItemEndpoint
import de.glasparlament.glasparlament.agendaItemDetail.domain.AgendaItemDetailRepository
import de.glasparlament.glasparlament.agendaItemDetail.domain.AgendaItemUseCase
import de.glasparlament.glasparlament.agendaItemOverview.application.AgendaItemFragment
import de.glasparlament.glasparlament.agendaItemOverview.application.AgendaItemViewModelFactory
import de.glasparlament.glasparlament.agendaItemOverview.data.AgendaItemRepositoryImpl
import de.glasparlament.glasparlament.agendaItemOverview.domain.AgendaItemListUseCase
import de.glasparlament.glasparlament.agendaItemOverview.domain.AgendaItemRepository
import de.glasparlament.glasparlament.meeting.data.MeetingApi
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
    fun provideAgendaItemRepository(api: MeetingApi): AgendaItemRepository {
        return AgendaItemRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAgendaItemDetailRepository(api: AgendaItemApi): AgendaItemDetailRepository {
        return AgendaItemDetailRepositoryImpl(api)
    }

    /** UseCase*/
    @Provides
    @Singleton
    fun provideAgendaItemListUseCase(repository: AgendaItemRepository): AgendaItemListUseCase {
        return AgendaItemListUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAgendaItemUseCase(repository: AgendaItemDetailRepository): AgendaItemUseCase {
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
        abstract fun contributeAgendaItemDetailFragment(): AgendaItemDetailFragment
    }
}