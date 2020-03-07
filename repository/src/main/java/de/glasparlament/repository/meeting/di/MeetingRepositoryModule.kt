package de.glasparlament.repository.meeting.di

import dagger.Module
import dagger.Provides
import de.glasparlament.data.db.AgendaItemDao
import de.glasparlament.data.db.FileDao
import de.glasparlament.data.db.MeetingDao
import de.glasparlament.repository.meeting.MeetingApi
import de.glasparlament.repository.meeting.MeetingEndpoint
import de.glasparlament.repository.meeting.MeetingRepository
import de.glasparlament.repository.meeting.MeetingRepositoryImpl
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
