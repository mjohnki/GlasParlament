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

    val uiModel = MutableLiveData<UIModel>()

    companion object {
        fun loading() = UIModel(progressBarVisibility = true, listVisibility = false)
        fun error() = UIModel(progressBarVisibility = true, listVisibility = false)
        fun loaded(agendaItems: List<AgendaItem>) = UIModel(
                progressBarVisibility = false,
                listVisibility = true,
                agendaItems = agendaItems)
    }
}

class AgendaItemViewModelImpl(private val useCase: AgendaItemListUseCase) : AgendaItemViewModel() {

    override fun bind(url: String) {
        viewModelScope.launch {
            getAgendaItems(url)
        }
    }

    private suspend fun getAgendaItems(url: String) = withContext(Dispatchers.Default) {
        uiModel.postValue(loading())
        when (val result = useCase.execute(url)) {
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
        val agendaItems: List<AgendaItem> = emptyList())
