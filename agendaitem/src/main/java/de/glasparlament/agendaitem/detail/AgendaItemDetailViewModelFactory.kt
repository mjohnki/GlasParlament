package de.glasparlament.agendaitem.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AgendaItemDetailViewModelFactory(private val useCase: AgendaItemUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AgendaItemDetailViewModelImpl(useCase) as T
    }
}
