package de.glasparlament.agendaitem.application.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.glasparlament.agendaitem.domain.AgendaItemListUseCase

class AgendaItemViewModelFactory(private val useCase: AgendaItemListUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AgendaItemViewModelImpl(useCase) as T
    }
}
