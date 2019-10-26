package de.glasparlament.organization.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.glasparlament.bodyRepository.BodyRepository
import de.glasparlament.organization.OrganizationListUseCase
import de.glasparlament.organization.OrganizationListViewModelFactory
import de.glasparlament.organization.OrganizationListFragment
import de.glasparlament.organizationRepository.OrganizationRepository
import javax.inject.Singleton

@Module(includes = [OrganizationModule.Binding::class])
class OrganizationModule {

    /** UseCase*/
    @Provides
    @Singleton
    fun provideOrganizationListUseCase(
            bodyRepository: BodyRepository,
            organizationRepository: OrganizationRepository)
            : OrganizationListUseCase {
        return OrganizationListUseCase(organizationRepository, bodyRepository)
    }

    /** ViewModelFactory*/
    @Provides
    @Singleton
    fun provideOrganizationListViewModelFactory(organizationListUseCase: OrganizationListUseCase)
            : OrganizationListViewModelFactory {
        return OrganizationListViewModelFactory(organizationListUseCase)
    }

    @Module
    abstract class Binding {

        @ContributesAndroidInjector
        abstract fun organizationActivity(): OrganizationListFragment
    }
}
