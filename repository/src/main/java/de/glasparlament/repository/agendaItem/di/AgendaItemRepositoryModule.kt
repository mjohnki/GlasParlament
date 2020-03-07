package de.glasparlament.repository.agendaItem.di

import dagger.Module
import dagger.Provides
import de.glasparlament.data.db.AgendaItemDao
import de.glasparlament.data.db.MeetingDao
import de.glasparlament.repository.agendaItem.AgendaItemRepository
import de.glasparlament.repository.agendaItem.AgendaItemRepositoryImpl
import javax.inject.Singleton

@Module
class AgendaItemRepositoryModule {

    @Provides
    @Singleton
    fun provideAgendaItemRepository(agendaItemDao: AgendaItemDao,
                                    meetingDao: MeetingDao) =
            AgendaItemRepositoryImpl(agendaItemDao, meetingDao) as AgendaItemRepository
}
