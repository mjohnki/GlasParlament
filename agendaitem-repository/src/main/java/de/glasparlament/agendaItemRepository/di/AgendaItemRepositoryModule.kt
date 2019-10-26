package de.glasparlament.agendaItemRepository.di

import dagger.Module
import dagger.Provides
import de.glasparlament.agendaItemRepository.AgendaItemRepository
import de.glasparlament.agendaItemRepository.AgendaItemRepositoryImpl
import de.glasparlament.data.db.AgendaItemDao
import de.glasparlament.data.db.MeetingDao
import javax.inject.Singleton


@Module
class AgendaItemRepositoryModule {

    @Provides
    @Singleton
    fun provideAgendaItemRepository(agendaItemDao: AgendaItemDao,
                                    meetingDao: MeetingDao) =
            AgendaItemRepositoryImpl(agendaItemDao, meetingDao) as AgendaItemRepository
}
