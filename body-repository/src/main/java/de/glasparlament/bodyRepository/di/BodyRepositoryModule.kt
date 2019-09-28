package de.glasparlament.bodyRepository.di

import de.glasparlament.bodyRepository.BodyApi
import de.glasparlament.bodyRepository.BodyEndpoint
import de.glasparlament.bodyRepository.BodyRepository
import de.glasparlament.bodyRepository.BodyRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val bodyRepositoryModule = module {
    single<BodyEndpoint> { provideBodyEndpoint(get()) }
    single<BodyApi> { provideBodyApi(get()) }
    single<BodyRepository> { provideBodyRepository(get()) }
}

fun provideBodyEndpoint(retrofit: Retrofit) =
        retrofit.create(BodyEndpoint::class.java)

fun provideBodyApi(endpoint: BodyEndpoint) =
        BodyApi(endpoint)

fun provideBodyRepository(api: BodyApi) =
        BodyRepositoryImpl(api)
