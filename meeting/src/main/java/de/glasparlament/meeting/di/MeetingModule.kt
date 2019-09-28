package de.glasparlament.meeting.di

import de.glasparlament.meeting.MeetingListUseCase
import de.glasparlament.meeting.MeetingViewModel
import de.glasparlament.meeting.MeetingViewModelImpl
import de.glasparlament.meetingRepository.MeetingRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val meetingModule = module {
    factory<MeetingListUseCase> { provideMeetingListUseCase(get()) }
    viewModel<MeetingViewModel> { provideMeetingListViewModel(get()) }
}

fun provideMeetingListUseCase(repository: MeetingRepository) =
        MeetingListUseCase(repository)

fun provideMeetingListViewModel(useCase: MeetingListUseCase) =
        MeetingViewModelImpl(useCase)
