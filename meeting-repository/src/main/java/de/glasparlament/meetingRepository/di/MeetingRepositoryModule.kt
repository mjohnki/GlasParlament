package de.glasparlament.meetingRepository.di

import de.glasparlament.data.db.AgendaItemDao
import de.glasparlament.data.db.FileDao
import de.glasparlament.data.db.MeetingDao
import de.glasparlament.meetingRepository.MeetingApi
import de.glasparlament.meetingRepository.MeetingEndpoint
import de.glasparlament.meetingRepository.MeetingRepository
import de.glasparlament.meetingRepository.MeetingRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val meetingRepositoryModule = module {
    single<MeetingEndpoint> { provideMeetingEndpoint(get()) }
    single<MeetingApi> { provideMeetingApi(get()) }
    single<MeetingRepository> { provideMeetingRepository(get(), get(), get(), get()) }
}

fun provideMeetingEndpoint(retrofit: Retrofit) =
        retrofit.create(MeetingEndpoint::class.java)

fun provideMeetingApi(endpoint: MeetingEndpoint) =
        MeetingApi(endpoint)

fun provideMeetingRepository(api: MeetingApi,
                             meetingDao: MeetingDao,
                             agendaItemDao: AgendaItemDao,
                             fileDao: FileDao): MeetingRepository =
        MeetingRepositoryImpl(api, meetingDao, agendaItemDao, fileDao)
