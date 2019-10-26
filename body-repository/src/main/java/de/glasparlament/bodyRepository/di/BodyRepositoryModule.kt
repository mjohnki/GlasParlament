package de.glasparlament.bodyRepository.di

import dagger.Module
import dagger.Provides
import de.glasparlament.bodyRepository.BodyApi
import de.glasparlament.bodyRepository.BodyEndpoint
import de.glasparlament.bodyRepository.BodyRepository
import de.glasparlament.bodyRepository.BodyRepositoryImpl
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class BodyRepositoryModule {

    @Provides
    @Singleton
    fun provideBodyEndpoint(retrofit: Retrofit) =
            retrofit.create(BodyEndpoint::class.java)

    @Provides
    @Singleton
    fun provideBodyApi(endpoint: BodyEndpoint) =
            BodyApi(endpoint)

    @Provides
    @Singleton
    fun provideBodyRepository(api: BodyApi) =
            BodyRepositoryImpl(api) as BodyRepository
}
