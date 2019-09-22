package de.glasparlament.glasparlament.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import de.glasparlament.data.db.AgendaItemDao
import de.glasparlament.data.db.FileDao
import de.glasparlament.data.db.GPDatabase
import de.glasparlament.data.db.MeetingDao
import de.glasparlament.meeting.MeetingListFragment
import de.glasparlament.meeting.MeetingViewModelFactory
import de.glasparlament.meeting_repository.MeetingApi
import de.glasparlament.meeting_repository.MeetingEndpoint
import de.glasparlament.meeting_repository.MeetingRepository
import de.glasparlament.meeting_repository.MeetingRepositoryImpl
import de.glasparlament.meeting.MeetingListUseCase
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
    fun provideMeetingRepository(
            api: MeetingApi,
            meetingDao : MeetingDao,
            agendaItemDao: AgendaItemDao,
            fileDao: FileDao): MeetingRepository {
        return MeetingRepositoryImpl(api, meetingDao, agendaItemDao, fileDao)
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

    /** DAO **/
    @Provides
    @Singleton
    fun provideMeetingDao(database: GPDatabase) : MeetingDao {
        return database.meetingDao()
    }

    @Module
    abstract class Binding {

        @ContributesAndroidInjector
        abstract fun MeetingActivity(): MeetingListFragment
    }
}