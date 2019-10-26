package de.glasparlament.agendaitem.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AgendaItemSearchViewModelFactory(private val useCase: AgendaItemSearchUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AgendaItemSearchViewModelImpl(useCase) as T
    }
}
