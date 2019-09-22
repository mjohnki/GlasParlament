package de.glasparlament.agendaitem.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.glasparlament.agendaItemRepository.AgendaItemSearchResult
import de.glasparlament.common.NavigationViewModel
import de.glasparlament.data.Transfer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class AgendaItemSearchViewModel : NavigationViewModel() {

    val uiModel = MutableLiveData<UIModel>()

    abstract fun search(text: String)

    companion object {
        fun loading() = UIModel(progressBarVisibility = true, listVisibility = false)
        fun error() = UIModel(progressBarVisibility = true, listVisibility = false)
        fun loaded(agendaItems: List<AgendaItemSearchResult>) = UIModel(
                progressBarVisibility = false,
                listVisibility = true,
                agendaItems = agendaItems)
    }
}

class AgendaItemSearchViewModelImpl(private val useCase: AgendaItemSearchUseCase) : AgendaItemSearchViewModel() {

    override fun search(text: String) {
        viewModelScope.launch {
            searchAgendaItems(text)
        }
    }

    private suspend fun searchAgendaItems(text: String) = withContext(Dispatchers.Default) {
        uiModel.postValue(loading())
        when (val result = useCase.execute(text)) {
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
        val agendaItems: List<AgendaItemSearchResult> = emptyList())
