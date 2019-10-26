package de.glasparlament.organization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OrganizationListViewModelFactory(private val organizationListUseCase: OrganizationListUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrganizationListViewModelImpl(organizationListUseCase) as T
    }
}
