package de.glasparlament.repository.body.di

import dagger.Module
import dagger.Provides
import de.glasparlament.repository.body.BodyApi
import de.glasparlament.repository.body.BodyEndpoint
import de.glasparlament.repository.body.BodyRepository
import de.glasparlament.repository.body.BodyRepositoryImpl
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
