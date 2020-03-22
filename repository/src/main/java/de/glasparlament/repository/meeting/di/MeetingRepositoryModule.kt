package de.glasparlament.repository.meeting.di

import com.dropbox.android.external.store4.MemoryPolicy
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import dagger.Module
import dagger.Provides
import de.glasparlament.repository.meeting.Meeting
import de.glasparlament.repository.meeting.MeetingRepository
import de.glasparlament.repository.meeting.MeetingRepositoryImpl
import de.glasparlament.repository.meeting.remote.MeetingApi
import de.glasparlament.repository.meeting.remote.MeetingEndpoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class MeetingRepositoryModule {

    @Provides
    @Singleton
    fun provideMeetingEndpoint(retrofit: Retrofit) =
            retrofit.create(MeetingEndpoint::class.java)

    @Provides
    @Singleton
    fun provideMeetingApi(endpoint: MeetingEndpoint) =
            MeetingApi(endpoint)

    /*@Provides
    @Singleton
    fun provideMeetingLocal(meetingDao: MeetingDao,
                            agendaItemDao: AgendaItemDao,
                            fileDao: FileDao) =
            MeetingLocal(meetingDao, agendaItemDao, fileDao)*/

    @Provides
    @Singleton
    fun provideMeetingRepository(store: Store<String, List<Meeting>>): MeetingRepository =
            MeetingRepositoryImpl(store)

    @Provides
    @Singleton
    @FlowPreview
    @ExperimentalCoroutinesApi
    fun provideMeetingStore(api: MeetingApi): Store<String, List<Meeting>> =
            StoreBuilder
                    .fromNonFlow<String, List<Meeting>> { key ->  api.getMeetingList(key) }
                    .cachePolicy(
                            MemoryPolicy.builder()
                                    .setMemorySize(MEMORY_SIZE)
                                    .setExpireAfterWrite(EXPIRE)
                                    .setExpireAfterTimeUnit(TimeUnit.SECONDS)
                                    .build()
                    ).build()

    companion object {
        private const val MEMORY_SIZE = 10L
        private const val EXPIRE = 10L
    }
}
