package de.glasparlament.organizationRepository.di

import de.glasparlament.organizationRepository.OrganizationApi
import de.glasparlament.organizationRepository.OrganizationEndpoint
import de.glasparlament.organizationRepository.OrganizationRepository
import de.glasparlament.organizationRepository.OrganizationRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val organizationRepositoryModule = module {
    single<OrganizationEndpoint> { provideOrganizationEndpoint(get()) }
    single<OrganizationApi> { provideOrganizationApi(get()) }
    single<OrganizationRepository> { provideOrganizationRepository(get()) }
}

fun provideOrganizationEndpoint(retrofit: Retrofit) =
        retrofit.create(OrganizationEndpoint::class.java)

fun provideOrganizationApi(endpoint: OrganizationEndpoint) =
        OrganizationApi(endpoint)

fun provideOrganizationRepository(organizationApi: OrganizationApi) =
        OrganizationRepositoryImpl(organizationApi)
