package de.glasparlament.agendaitem.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.android.external.store4.StoreResponse
import de.glasparlament.repository.agendaItem.AgendaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
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
            withContext(Dispatchers.IO) {
                useCase.execute(url).collect {
                    when (it) {
                        is StoreResponse.Loading -> state.postValue(State.Loading)
                        is StoreResponse.Data -> state.postValue(State.Loaded(it.value))
                        is StoreResponse.Error -> state.postValue(State.Error)

                    }
                }
            }
}
