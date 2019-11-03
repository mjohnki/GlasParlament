package de.glasparlament.search.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.glasparlament.agendaItemRepository.AgendaItemSearchResult
import de.glasparlament.data.Transfer
import de.glasparlament.search.useCase.SearchUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class SearchViewModel : ViewModel() {

    val state = MutableLiveData<State>()

    abstract fun search(text: String)

    sealed class State {
        object Loading : State()
        object Error : State()
        data class Loaded(val agendaItems: List<AgendaItemSearchResult>) : State()
    }
}

class SearchViewModelImpl(private val useCase: SearchUseCase) : SearchViewModel() {

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
