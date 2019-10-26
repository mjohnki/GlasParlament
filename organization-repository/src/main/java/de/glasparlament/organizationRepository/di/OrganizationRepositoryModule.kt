package de.glasparlament.organizationRepository.di

import dagger.Module
import dagger.Provides
import de.glasparlament.organizationRepository.OrganizationApi
import de.glasparlament.organizationRepository.OrganizationEndpoint
import de.glasparlament.organizationRepository.OrganizationRepository
import de.glasparlament.organizationRepository.OrganizationRepositoryImpl
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class OrganizationRepositoryModule {

    @Provides
    @Singleton
    fun provideOrganizationApi(endpoint: OrganizationEndpoint): OrganizationApi {
        return OrganizationApi(endpoint)
    }

    @Provides
    @Singleton
    fun provideOrganizationEndpoint(retrofit: Retrofit): OrganizationEndpoint {
        return retrofit.create(OrganizationEndpoint::class.java)
    }

    @Provides
    @Singleton
    fun provideOrganizationRepository(organizationApi: OrganizationApi): OrganizationRepository {
        return OrganizationRepositoryImpl(organizationApi)
    }
}
