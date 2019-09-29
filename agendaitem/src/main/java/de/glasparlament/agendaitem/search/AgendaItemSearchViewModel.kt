package de.glasparlament.agendaitem.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.glasparlament.agendaItemRepository.AgendaItem
import de.glasparlament.agendaItemRepository.AgendaItemSearchResult
import de.glasparlament.common.NavigationViewModel
import de.glasparlament.data.Transfer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class AgendaItemSearchViewModel : NavigationViewModel() {

    val state = MutableLiveData<State>()

    abstract fun search(text: String)

    sealed class State {
        object Loading : State()
        object Error : State()
        data class Loaded(val agendaItems: List<AgendaItemSearchResult>) : State()
    }
}

class AgendaItemSearchViewModelImpl(private val useCase: AgendaItemSearchUseCase) : AgendaItemSearchViewModel() {

    override fun search(text: String) {
        viewModelScope.launch {
            searchAgendaItems(text)
        }
    }

    private suspend fun searchAgendaItems(text: String) =
            withContext(Dispatchers.Default) {
                state.postValue(State.Loading)
                when (val result = useCase.execute(text)) {
                    is Transfer.Success -> state.postValue(State.Loaded(result.data))
                    is Transfer.Error -> state.postValue(State.Error)
                }
            }
}
