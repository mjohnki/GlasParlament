package de.glasparlament.search.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.glasparlament.search.useCase.SearchUseCase

class SearchViewModelFactory(private val useCase: SearchUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModelImpl(useCase) as T
    }
}
