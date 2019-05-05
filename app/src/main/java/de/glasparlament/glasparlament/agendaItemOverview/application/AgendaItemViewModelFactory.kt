package de.glasparlament.glasparlament.agendaItemOverview.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.glasparlament.glasparlament.agendaItemOverview.domain.AgendaItemListUseCase

class AgendaItemViewModelFactory(private val useCase: AgendaItemListUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AgendaItemViewModel(useCase) as T
    }
}
