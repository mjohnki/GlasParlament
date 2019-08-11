package de.glasparlament.glasparlament.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.glasparlament.body_repository.BodyApi
import de.glasparlament.body_repository.BodyEndpoint
import de.glasparlament.organization.OrganizationListViewModelFactory
import de.glasparlament.body_repository.BodyRepository
import de.glasparlament.body_repository.BodyRepositoryImpl
import de.glasparlament.organization.OrganizationListFragment
import de.glasparlament.organization_repository.OrganizationRepository
import de.glasparlament.organization_repository.OrganizationRepositoryImpl
import de.glasparlament.organization.OrganizationListUseCase
import de.glasparlament.organization_repository.OrganizationApi
import de.glasparlament.organization_repository.OrganizationEndpoint
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
        return retrofit.create(BodyEndpoint::class.java)
    }

    @Provides
    @Singleton
    fun provideOrganizationEndpoint(retrofit: Retrofit): OrganizationEndpoint {
        return retrofit.create(OrganizationEndpoint::class.java)
    }

    /** Repository*/
    @Provides
    @Singleton
    fun provideOrganizationRepository(organizationApi: OrganizationApi): OrganizationRepository {
        return OrganizationRepositoryImpl(organizationApi)
    }

    @Provides
    @Singleton
    fun provideBodyRepository(api: BodyApi): BodyRepository {
        return BodyRepositoryImpl(api)
    }

    /** UseCase*/
    @Provides
    @Singleton
    fun provideOrganizationListUseCase(bodyRepository: BodyRepository, organizationRepository: OrganizationRepository): OrganizationListUseCase {
        return OrganizationListUseCase(organizationRepository, bodyRepository)
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
        abstract fun OrganizationActivity(): OrganizationListFragment
    }
}