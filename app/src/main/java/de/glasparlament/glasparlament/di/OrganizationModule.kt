package de.glasparlament.glasparlament.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.glasparlament.glasparlament.organization.application.OrganizationListFragment
import de.glasparlament.glasparlament.organization.application.OrganizationListViewModelFactory
import de.glasparlament.glasparlament.organization.data.*
import de.glasparlament.glasparlament.organization.domain.OrganizationListUseCase
import de.glasparlament.glasparlament.organization.domain.OrganizationRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [OrganizationModule.Binding::class])
class OrganizationModule {

    /** Api */
    @Provides
    @Singleton
    fun provideBodyApi(endpoint: BodyEndpoint): BodyApi {
        return BodyApi(endpoint)
    }

    @Provides
    @Singleton
    fun provideOrganizationApi(endpoint: OrganizationEndpoint): OrganizationApi {
        return OrganizationApi(endpoint)
    }

    /** Endpoint */
    @Provides
    @Singleton
    fun provideBodyEndpoint(retrofit: Retrofit): BodyEndpoint {
        return retrofit
                .create(BodyEndpoint::class.java)
    }

    @Provides
    @Singleton
    fun provideOrganizationEndpoint(retrofit: Retrofit): OrganizationEndpoint {
        return retrofit
                .create(OrganizationEndpoint::class.java)
    }

    /** Repository*/
    @Provides
    @Singleton
    fun provideOrganizationRepository(bodyApi: BodyApi, organizationApi: OrganizationApi): OrganizationRepository {
        return OrganizationRepositoryImpl(bodyApi, organizationApi)
    }

    /** UseCase*/
    @Provides
    @Singleton
    fun provideOrganizationListUseCase(organizationRepository: OrganizationRepository): OrganizationListUseCase {
        return OrganizationListUseCase(organizationRepository)
    }

    /** ViewModelFactory*/
    @Provides
    @Singleton
    fun provideOrganizationListViewModelFactory(organizationListUseCase: OrganizationListUseCase): OrganizationListViewModelFactory {
        return OrganizationListViewModelFactory(organizationListUseCase)
    }


    @Module
    abstract class Binding {

        @ContributesAndroidInjector
        abstract fun contributeOrganizationListFragment(): OrganizationListFragment
    }
}