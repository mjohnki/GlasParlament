package de.glasparlament.agendaitem.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.glasparlament.agendaItemRepository.AgendaItem
import de.glasparlament.common.NavigationViewModel
import de.glasparlament.data.Transfer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class AgendaItemViewModel : NavigationViewModel() {

    abstract fun bind(url: String)

    val state = MutableLiveData<State>()

    val uiModel = MutableLiveData<UIModel>()

    sealed class State {
        object Loading : State()
        object Error : State()
        data class Loaded(val agendaItems: List<AgendaItem>) : State()
    }
}

class AgendaItemViewModelImpl(private val useCase: AgendaItemListUseCase) : AgendaItemViewModel() {

    override fun bind(url: String) {
        viewModelScope.launch {
            getAgendaItems(url)
        }
    }

    private suspend fun getAgendaItems(url: String) =
            withContext(Dispatchers.Default) {
                state.postValue(State.Loading)
                when (val result = useCase.execute(url)) {
                    is Transfer.Success -> state.postValue(State.Loaded(result.data))
                    is Transfer.Error -> state.postValue(State.Error)
                }
            }
}

data class UIModel(
                val progressBarVisibility: Boolean,
                val listVisibility: Boolean,
                val agendaItems: List<AgendaItem> = emptyList())
