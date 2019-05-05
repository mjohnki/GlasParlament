package de.glasparlament.glasparlament.organization.application

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.glasparlament.glasparlament.organization.data.BodyOrganization
import de.glasparlament.glasparlament.ext.addTo
import de.glasparlament.glasparlament.organization.domain.OrganizationListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class OrganizationListViewModel(organizationListUseCase: OrganizationListUseCase) : ViewModel() {

    private val disposables = CompositeDisposable()
    val organizations = MutableLiveData<List<BodyOrganization>>()
    private val showErrorGettingOrganizations = MutableLiveData<Boolean>()
    val progressVisible = MutableLiveData<Boolean>()

    init {
        organizationListUseCase.execute().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { handleGetOrganizationListResult(it) }
                .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun handleGetOrganizationListResult(result: OrganizationListUseCase.Result) {
        progressVisible.postValue(result == OrganizationListUseCase.Result.Loading)
        when (result) {
            is OrganizationListUseCase.Result.Success -> {
                organizations.postValue(result.data)
            }
            is OrganizationListUseCase.Result.Failure -> {
                showErrorGettingOrganizations.postValue(true)
            }
        }
    }
}
