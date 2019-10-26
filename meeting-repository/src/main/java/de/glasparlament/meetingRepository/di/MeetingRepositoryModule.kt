package de.glasparlament.meetingRepository.di

import dagger.Module
import dagger.Provides
import de.glasparlament.data.db.AgendaItemDao
import de.glasparlament.data.db.FileDao
import de.glasparlament.data.db.MeetingDao
import de.glasparlament.meetingRepository.MeetingApi
import de.glasparlament.meetingRepository.MeetingEndpoint
import de.glasparlament.meetingRepository.MeetingRepository
import de.glasparlament.meetingRepository.MeetingRepositoryImpl
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class MeetingRepositoryModule {

    @Provides
    @Singleton
    fun provideMeetingEndpoint(retrofit: Retrofit) =
            retrofit.create(MeetingEndpoint::class.java)

    @Provides
    @Singleton
    fun provideMeetingApi(endpoint: MeetingEndpoint) =
            MeetingApi(endpoint)

    @Provides
    @Singleton
    fun provideMeetingRepository(api: MeetingApi,
                                 meetingDao: MeetingDao,
                                 agendaItemDao: AgendaItemDao,
                                 fileDao: FileDao): MeetingRepository =
            MeetingRepositoryImpl(api, meetingDao, agendaItemDao, fileDao)
}
