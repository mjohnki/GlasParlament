package de.glasparlament.glasparlament.meeting.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.glasparlament.glasparlament.meeting.domain.MeetingListUseCase

class MeetingViewModelFactory(
        private val useCase: MeetingListUseCase)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MeetingViewModel(useCase) as T
    }
}
