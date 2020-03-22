package de.glasparlament.search.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.android.external.store4.StoreResponse
import de.glasparlament.repository.agendaItem.AgendaItemSearchResult
import de.glasparlament.search.useCase.SearchUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
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
            withContext(Dispatchers.IO) {
                useCase.execute(text).collect {
                    when (it) {
                        is StoreResponse.Loading -> state.postValue(State.Loading)
                        is StoreResponse.Data -> state.postValue(State.Loaded(it.value))
                        is StoreResponse.Error -> state.postValue(State.Error)
                    }
                }
            }
}
