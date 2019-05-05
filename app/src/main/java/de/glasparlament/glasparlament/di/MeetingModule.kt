package de.glasparlament.glasparlament.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.glasparlament.glasparlament.meeting.application.MeetingListFragment
import de.glasparlament.glasparlament.meeting.application.MeetingViewModelFactory
import de.glasparlament.glasparlament.meeting.data.MeetingApi
import de.glasparlament.glasparlament.meeting.data.MeetingEndpoint
import de.glasparlament.glasparlament.meeting.data.MeetingRepositoryImpl
import de.glasparlament.glasparlament.meeting.domain.MeetingListUseCase
import de.glasparlament.glasparlament.meeting.domain.MeetingRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [MeetingModule.Binding::class])
class MeetingModule {

    /** Api */
    @Provides
    @Singleton
    fun provideMeetingApi(endpoint: MeetingEndpoint): MeetingApi {
        return MeetingApi(endpoint)
    }

    /** Endpoint */
    @Provides
    @Singleton
    fun provideMeetingEndpoint(retrofit: Retrofit): MeetingEndpoint {
        return retrofit
                .create(MeetingEndpoint::class.java)
    }

    /** Repository*/
    @Provides
    @Singleton
    fun provideMeetingRepository(api: MeetingApi): MeetingRepository {
        return MeetingRepositoryImpl(api)
    }

    /** UseCase*/
    @Provides
    @Singleton
    fun provideMeetingListUseCase(repository: MeetingRepository): MeetingListUseCase {
        return MeetingListUseCase(repository)
    }

    /** ViewModelFactory*/
    @Provides
    @Singleton
    fun provideMeetingListViewModelFactory(useCase: MeetingListUseCase): MeetingViewModelFactory {
        return MeetingViewModelFactory(useCase)
    }


    @Module
    abstract class Binding {

        @ContributesAndroidInjector
        abstract fun contributeMeetingListFragment(): MeetingListFragment
    }
}