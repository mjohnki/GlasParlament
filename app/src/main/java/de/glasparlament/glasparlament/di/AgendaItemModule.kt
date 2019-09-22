package de.glasparlament.glasparlament.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.glasparlament.agendaitem.detail.AgendaItemDetailViewModelFactory
import de.glasparlament.agendaitem.overview.AgendaItemFragment
import de.glasparlament.agendaitem.overview.AgendaItemViewModelFactory
import de.glasparlament.agendaItemRepository.AgendaItemEndpoint
import de.glasparlament.agendaitem.overview.AgendaItemListUseCase
import de.glasparlament.agendaitem.detail.AgendaItemUseCase
import de.glasparlament.agendaitem.search.AgendaItemSearchFragment
import de.glasparlament.agendaitem.search.AgendaItemSearchUseCase
import de.glasparlament.agendaitem.search.AgendaItemSearchViewModelFactory
import de.glasparlament.agendaItemRepository.AgendaItemApi
import de.glasparlament.agendaItemRepository.AgendaItemRepository
import de.glasparlament.agendaItemRepository.AgendaItemRepositoryImpl
import de.glasparlament.data.db.AgendaItemDao
import de.glasparlament.data.db.FileDao
import de.glasparlament.data.db.GPDatabase
import de.glasparlament.data.db.MeetingDao
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
    fun provideAgendaItemRepository(
            agendaItemDao: AgendaItemDao,
            meetingDao: MeetingDao): AgendaItemRepository {
        return AgendaItemRepositoryImpl(agendaItemDao, meetingDao)
    }

    /** UseCase*/
    @Provides
    @Singleton
    fun provideAgendaItemListUseCase(agendaItemRepository: AgendaItemRepository): AgendaItemListUseCase {
        return AgendaItemListUseCase(agendaItemRepository)
    }

    @Provides
    @Singleton
    fun provideAgendaItemSearchUseCase(agendaItemRepository: AgendaItemRepository): AgendaItemSearchUseCase {
        return AgendaItemSearchUseCase(agendaItemRepository)
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

    @Provides
    @Singleton
    fun provideAgendaItemSearchViewModelFactory(useCase: AgendaItemSearchUseCase): AgendaItemSearchViewModelFactory {
        return AgendaItemSearchViewModelFactory(useCase)
    }

    /** DAO **/
    @Provides
    @Singleton
    fun provideMeetingDao(database: GPDatabase): AgendaItemDao {
        return database.agendaItemDao()
    }

    @Provides
    @Singleton
    fun provideFileDao(database: GPDatabase): FileDao {
        return database.fileDao()
    }

    @Module
    abstract class Binding {

        @ContributesAndroidInjector
        abstract fun contributeAgendaItemListFragment(): AgendaItemFragment

        @ContributesAndroidInjector
        abstract fun contributeAgendaItemSearchFragment(): AgendaItemSearchFragment

        @ContributesAndroidInjector
        abstract fun contributeAgendaItemDetailFragment(): de.glasparlament.agendaitem.detail.AgendaItemDetailFragment
    }
}
