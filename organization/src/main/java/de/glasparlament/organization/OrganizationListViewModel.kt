package de.glasparlament.organization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import de.glasparlament.common_android.NavigationCommand
import de.glasparlament.data.Transfer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class OrganizationListViewModel : ViewModel() {

    val uiModel = MutableLiveData<UIModel>()
    val navigationCommand = MutableLiveData<NavigationCommand>()

    abstract fun navigate(directions: NavDirections)
    abstract fun loadData()

    companion object {
        fun loading() = UIModel(progressBarVisibility = true, listVisibility = false)
        fun error() = UIModel(progressBarVisibility = true, listVisibility = false)
        fun loaded(organizations: List<BodyOrganization>) = UIModel(progressBarVisibility = false, listVisibility = true, organizations = organizations)
    }
}

class OrganizationListViewModelImpl(private val useCase: OrganizationListUseCase) : OrganizationListViewModel() {

    init {
        uiModel.postValue(loading())
    }

    override fun loadData(){
        viewModelScope.launch {
            getOrganizations()
        }
    }


    private suspend fun getOrganizations() = withContext(Dispatchers.Default) {
        when (val result = useCase.execute()) {
            is Transfer.Success -> {
                uiModel.postValue(loaded(result.data))
            }
            is Transfer.Error -> {
                uiModel.postValue(error())
            }
        }
    }

    override fun navigate(directions: NavDirections) {
        navigationCommand.postValue(NavigationCommand.To(directions))
    }
}

data class UIModel(
        val progressBarVisibility: Boolean,
        val listVisibility: Boolean,
        val organizations: List<BodyOrganization> = emptyList())
