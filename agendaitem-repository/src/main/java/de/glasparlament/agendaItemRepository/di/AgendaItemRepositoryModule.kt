package de.glasparlament.agendaItemRepository.di

import de.glasparlament.agendaItemRepository.AgendaItemApi
import de.glasparlament.agendaItemRepository.AgendaItemEndpoint
import de.glasparlament.agendaItemRepository.AgendaItemRepository
import de.glasparlament.agendaItemRepository.AgendaItemRepositoryImpl
import de.glasparlament.data.db.AgendaItemDao
import de.glasparlament.data.db.MeetingDao
import org.koin.dsl.module
import retrofit2.Retrofit

val agendaItemRepositoryModule = module {
    single<AgendaItemEndpoint> { provideAgendaItemEndpoint(get()) }
    single<AgendaItemApi> { provideAgendaItemApi(get()) }
    single<AgendaItemRepository> { provideAgendaItemRepository(get(), get()) }
}

fun provideAgendaItemEndpoint(retrofit: Retrofit) =
        retrofit.create(AgendaItemEndpoint::class.java)

fun provideAgendaItemApi(endpoint: AgendaItemEndpoint) =
        AgendaItemApi(endpoint)

fun provideAgendaItemRepository(agendaItemDao: AgendaItemDao,
                                meetingDao: MeetingDao) =
        AgendaItemRepositoryImpl(agendaItemDao, meetingDao)

