package de.glasparlament.organizationRepository.di

import dagger.Module
import dagger.Provides
import de.glasparlament.organizationRepository.OrganizationEndpoint
import io.mockk.every
import io.mockk.mockk
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class TestApplicationModule {
    @Provides
    fun provideRetrofit(): Retrofit {
        val retrofit: Retrofit = mockk()
        val endpoint: OrganizationEndpoint = mockk()

        val service: Class<OrganizationEndpoint> = OrganizationEndpoint::class.java
        every { retrofit.create(eq(service)) } returns endpoint

        return retrofit
    }
}