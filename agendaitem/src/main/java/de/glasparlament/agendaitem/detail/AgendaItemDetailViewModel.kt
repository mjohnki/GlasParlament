package de.glasparlament.agendaitem.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import de.glasparlament.data.AgendaItem
import de.glasparlament.common_android.NavigationCommand
import de.glasparlament.data.Transfer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class AgendaItemDetailViewModel : ViewModel() {

    abstract fun bind(url: String)

    val uiModel = MutableLiveData<UIModel>()
    val navigationCommand = MutableLiveData<NavigationCommand>()

    abstract fun navigate(directions: NavDirections)

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

    override fun navigate(directions: NavDirections) {
        navigationCommand.postValue(NavigationCommand.To(directions))
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
