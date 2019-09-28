package de.glasparlament.agendaItemRepository.di

import de.glasparlament.agendaItemRepository.AgendaItemRepository
import de.glasparlament.agendaItemRepository.AgendaItemRepositoryImpl
import de.glasparlament.data.db.AgendaItemDao
import de.glasparlament.data.db.MeetingDao
import org.koin.dsl.module
import retrofit2.Retrofit

val agendaItemRepositoryModule = module {
    single<AgendaItemRepository> { provideAgendaItemRepository(get(), get()) }
}

fun provideAgendaItemRepository(agendaItemDao: AgendaItemDao,
                                meetingDao: MeetingDao) =
        AgendaItemRepositoryImpl(agendaItemDao, meetingDao)
