package de.glasparlament.organization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.glasparlament.common.NavigationViewModel
import de.glasparlament.data.Transfer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class OrganizationListViewModel : NavigationViewModel() {

    val uiModel = MutableLiveData<UIModel>()

    abstract fun loadData()

    companion object {
        fun loading() = UIModel(progressBarVisibility = true, listVisibility = false)
        fun error() = UIModel(progressBarVisibility = true, listVisibility = false)
        fun loaded(organizations: List<BodyOrganization>) = UIModel(
                progressBarVisibility = false,
                listVisibility = true,
                organizations = organizations)
    }
}

class OrganizationListViewModelImpl(private val useCase: OrganizationListUseCase) : OrganizationListViewModel() {

    override fun loadData(){
        viewModelScope.launch {
            getOrganizations()
        }
    }

    private suspend fun getOrganizations() = withContext(Dispatchers.Default) {
        uiModel.postValue(loading())
        when (val result = useCase.execute()) {
            is Transfer.Success -> {
                uiModel.postValue(loaded(result.data))
            }
            is Transfer.Error -> {
                uiModel.postValue(error())
            }
        }
    }
}

data class UIModel(
        val progressBarVisibility: Boolean,
        val listVisibility: Boolean,
        val organizations: List<BodyOrganization> = emptyList())
