package de.glasparlament.repository.meeting.di

import dagger.Module
import dagger.Provides
import de.glasparlament.data.db.AgendaItemDao
import de.glasparlament.data.db.FileDao
import de.glasparlament.data.db.MeetingDao
import de.glasparlament.repository.meeting.MeetingEndpoint
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import retrofit2.Retrofit

@Module
class TestApplicationModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        val retrofit: Retrofit = mockk()
        val endpoint: MeetingEndpoint = mockk()

        val service: Class<MeetingEndpoint> = MeetingEndpoint::class.java
        every { retrofit.create(eq(service)) } returns endpoint

        return retrofit
    }

    @Provides
    fun provideFileDao(): FileDao =
            mockk(relaxed = true)

    @Provides
    fun provideAgendaItemDao(): AgendaItemDao =
            mockk(relaxed = true)

    @Provides
    fun provideMeetingDao(): MeetingDao {
        val dao: MeetingDao = mockk(relaxed = true)
        coEvery { dao.getMeetings() } returns emptyList()

        return dao
    }
}