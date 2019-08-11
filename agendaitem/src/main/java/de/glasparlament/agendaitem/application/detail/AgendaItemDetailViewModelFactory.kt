package de.glasparlament.agendaitem.application.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.glasparlament.agendaitem.domain.AgendaItemUseCase

class AgendaItemDetailViewModelFactory(private val useCase: AgendaItemUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AgendaItemDetailViewModelImpl(useCase) as T
    }
}
