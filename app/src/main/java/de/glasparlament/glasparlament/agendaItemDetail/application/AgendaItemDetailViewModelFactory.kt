package de.glasparlament.glasparlament.agendaItemDetail.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.glasparlament.glasparlament.agendaItemDetail.domain.AgendaItemUseCase

class AgendaItemDetailViewModelFactory(private val useCase: AgendaItemUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AgendaItemViewModel(useCase) as T
    }
}
