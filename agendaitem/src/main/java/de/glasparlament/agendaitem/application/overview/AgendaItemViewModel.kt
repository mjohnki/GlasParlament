package de.glasparlament.agendaitem.application.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import de.glasparlament.agendaitem.domain.AgendaItemListUseCase
import de.glasparlament.common_android.NavigationCommand
import de.glasparlament.data.AgendaItem
import de.glasparlament.data.Transfer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class AgendaItemViewModel : ViewModel() {
    abstract fun bind(url: String)

    val uiModel = MutableLiveData<UIModel>()
    val navigationCommand = MutableLiveData<NavigationCommand>()

    abstract fun navigate(directions: NavDirections)

    companion object {
        fun loading() = UIModel(progressBarVisibility = true, listVisibility = false)
        fun error() = UIModel(progressBarVisibility = true, listVisibility = false)
        fun loaded(agendaItems: List<AgendaItem>) = UIModel(progressBarVisibility = false, listVisibility = true, agendaItems = agendaItems)
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

    override fun navigate(directions: NavDirections) {
        navigationCommand.postValue(NavigationCommand.To(directions))
    }
}

data class UIModel(
        val progressBarVisibility: Boolean,
        val listVisibility: Boolean,
        val agendaItems: List<AgendaItem> = emptyList())