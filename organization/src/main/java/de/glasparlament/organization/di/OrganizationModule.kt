package de.glasparlament.organization.di

import de.glasparlament.bodyRepository.BodyRepository
import de.glasparlament.organization.OrganizationListUseCase
import de.glasparlament.organization.OrganizationListViewModel
import de.glasparlament.organization.OrganizationListViewModelImpl
import de.glasparlament.organizationRepository.OrganizationRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val organizationModule = module {
    factory<OrganizationListUseCase> { provideOrganizationListUseCase(get(), get()) }
    viewModel<OrganizationListViewModel> { provideOrganizationListViewModelFactory(get()) }
}

fun provideOrganizationListUseCase(bodyRepository: BodyRepository,
                                   organizationRepository: OrganizationRepository) =
        OrganizationListUseCase(organizationRepository, bodyRepository)

fun provideOrganizationListViewModelFactory(organizationListUseCase: OrganizationListUseCase) =
        OrganizationListViewModelImpl(organizationListUseCase)
