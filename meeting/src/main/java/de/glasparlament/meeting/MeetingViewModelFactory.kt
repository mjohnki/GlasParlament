package de.glasparlament.meeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MeetingViewModelFactory(
        private val useCase: MeetingListUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MeetingViewModelImpl(useCase) as T
    }
}
