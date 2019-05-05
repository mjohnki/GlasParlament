package de.glasparlament.glasparlament.organization.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.glasparlament.glasparlament.organization.domain.OrganizationListUseCase

class OrganizationListViewModelFactory(private val organizationListUseCase: OrganizationListUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrganizationListViewModel(organizationListUseCase) as T
    }
}
