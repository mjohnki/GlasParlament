package de.glasparlament.organization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.glasparlament.common.NavigationViewModel
import de.glasparlament.data.Transfer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class OrganizationListViewModel : NavigationViewModel() {

    val state = MutableLiveData<State>()

    abstract fun loadData()

    sealed class State {
        object Loading : State()
        object Error : State()
        data class Loaded(val meetings: List<BodyOrganization>) : State()
    }
}

class OrganizationListViewModelImpl(private val useCase: OrganizationListUseCase) : OrganizationListViewModel() {

    override fun loadData() {
        viewModelScope.launch {
            getOrganizations()
        }
    }

    private suspend fun getOrganizations() = withContext(Dispatchers.Default) {
        state.postValue(State.Loading)
        when (val result = useCase.execute()) {
            is Transfer.Success -> {
                state.postValue(State.Loaded(result.data))
            }
            is Transfer.Error -> {
                state.postValue(State.Error)
            }
        }
    }
}
