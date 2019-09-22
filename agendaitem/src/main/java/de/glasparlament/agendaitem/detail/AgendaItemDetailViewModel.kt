package de.glasparlament.agendaitem.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.glasparlament.agendaItemRepository.AgendaItem
import de.glasparlament.common.NavigationViewModel
import de.glasparlament.data.Transfer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class AgendaItemDetailViewModel : NavigationViewModel() {

    abstract fun bind(url: String)

    val uiModel = MutableLiveData<UIModel>()

    companion object {
        fun loading() = UIModel()
        fun error() = UIModel()
        fun loaded(agendaItem: AgendaItem) = UIModel(agendaItem = agendaItem)
    }
}

class AgendaItemDetailViewModelImpl(private val useCase: AgendaItemUseCase) : AgendaItemDetailViewModel() {

    override fun bind(url: String) {
        viewModelScope.launch {
            getAgendaItem(url)
        }
    }

    private suspend fun getAgendaItem(url: String) = withContext(Dispatchers.Default) {
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
        val agendaItem: AgendaItem? = null)
