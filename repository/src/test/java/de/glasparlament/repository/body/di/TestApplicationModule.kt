package de.glasparlament.repository.body.di

import dagger.Module
import dagger.Provides
import de.glasparlament.repository.body.BodyEndpoint
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