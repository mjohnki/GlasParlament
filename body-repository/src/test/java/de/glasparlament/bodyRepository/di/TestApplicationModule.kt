package de.glasparlament.bodyRepository.di

import dagger.Module
import dagger.Provides
import de.glasparlament.bodyRepository.BodyEndpoint
import de.glasparlament.data.db.AgendaItemDao
import de.glasparlament.data.db.FileDao
import de.glasparlament.data.db.MeetingDao
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import retrofit2.Retrofit

@Module
class TestApplicationModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        val retrofit: Retrofit = mockk()
        val endpoint: BodyEndpoint = mockk()

        val service: Class<BodyEndpoint> = BodyEndpoint::class.java
        every { retrofit.create(eq(service)) } returns endpoint

        return retrofit
    }
}