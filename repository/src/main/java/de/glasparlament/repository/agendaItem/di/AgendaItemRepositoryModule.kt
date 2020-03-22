package de.glasparlament.repository.agendaItem.di

import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import dagger.Module
import dagger.Provides
import de.glasparlament.repository.agendaItem.AgendaItem
import de.glasparlament.repository.agendaItem.AgendaItemRepository
import de.glasparlament.repository.agendaItem.AgendaItemRepositoryImpl
import de.glasparlament.repository.agendaItem.AgendaItemSearchResult
import de.glasparlament.repository.agendaItem.local.AgendaItemDao
import de.glasparlament.repository.agendaItem.local.AgendaItemLocal
import de.glasparlament.repository.meeting.local.meeting.MeetingDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@Module
class AgendaItemRepositoryModule {

    @Provides
    @Singleton
    @FlowPreview
    @ExperimentalCoroutinesApi
    fun provideAgendaItemStore(local: AgendaItemLocal): Store<String, AgendaItem> =
            StoreBuilder
                    .fromNonFlow<String, AgendaItem> { key -> local.getAgendaItem(key) }
                    .disableCache()
                    .build()

    @Provides
    @Singleton
    @FlowPreview
    @ExperimentalCoroutinesApi
    fun provideAgendaItemsStore(local: AgendaItemLocal): Store<String, List<AgendaItem>> =
            StoreBuilder
                    .fromNonFlow<String, List<AgendaItem>> { key -> local.getAgendaItems(key) }
                    .disableCache()
                    .build()

    @Provides
    @Singleton
    @FlowPreview
    @ExperimentalCoroutinesApi
    fun provideAgendaItemSearchStore(local: AgendaItemLocal): Store<String, List<AgendaItemSearchResult>> =
            StoreBuilder
                    .fromNonFlow<String, List<AgendaItemSearchResult>> { key -> local.searchAgendaItems(key) }
                    .disableCache()
                    .build()


    @Provides
    fun provideAgendaItemLocal(agendaItemDao: AgendaItemDao, meetingDao: MeetingDao) =
            AgendaItemLocal(agendaItemDao, meetingDao)

    @Provides
    @Singleton
    fun provideAgendaItemRepository(agendaItemSearchStore: Store<String, List<AgendaItemSearchResult>>,
                                    agendaItemsStore : Store<String, List<AgendaItem>>,
                                    agendaItemStore : Store<String, AgendaItem>) =
            AgendaItemRepositoryImpl(agendaItemSearchStore, agendaItemsStore, agendaItemStore) as AgendaItemRepository
}
