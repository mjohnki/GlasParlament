package de.glasparlament.meeting.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.glasparlament.meeting.MeetingListFragment
import de.glasparlament.meeting.MeetingListUseCase
import de.glasparlament.meeting.MeetingViewModelFactory
import de.glasparlament.meetingRepository.MeetingRepository
import javax.inject.Singleton

@Module(includes = [MeetingModule.Binding::class])
class MeetingModule {

    @Provides
    fun provideMeetingListUseCase(repository: MeetingRepository) =
            MeetingListUseCase(repository)

    @Provides
    @Singleton
    fun provideMeetingViewModelFactory(useCase: MeetingListUseCase) =
            MeetingViewModelFactory(useCase)

    @Module
    abstract class Binding {

        @ContributesAndroidInjector
        abstract fun meetingListFragment(): MeetingListFragment
    }
}
