package de.glasparlament.agendaitem.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.glasparlament.repository.agendaItem.AgendaItem
import de.glasparlament.data.Transfer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class AgendaItemDetailViewModel : ViewModel() {

    abstract fun bind(url: String)

    val state = MutableLiveData<State>()

    sealed class State {
        object Loading : State()
        object Error : State()
        data class Loaded(val agendaItem: AgendaItem) : State()
    }
}

class AgendaItemDetailViewModelImpl(private val useCase: AgendaItemUseCase) : AgendaItemDetailViewModel() {

    override fun bind(url: String) {
        viewModelScope.launch {
            getAgendaItem(url)
        }
    }

    private suspend fun getAgendaItem(url: String) =
            withContext(Dispatchers.Default) {
                state.postValue(State.Loading)
                when (val result = useCase.execute(url)) {
                    is Transfer.Success -> state.postValue(State.Loaded(result.data))
                    is Transfer.Error -> state.postValue(State.Error)

                }
            }
}
