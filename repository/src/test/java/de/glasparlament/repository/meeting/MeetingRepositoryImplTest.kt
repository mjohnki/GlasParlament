package de.glasparlament.repository.meeting

import com.dropbox.android.external.store4.Store
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class MeetingRepositoryImplTest {

    private val store: Store<String, List<Meeting>> = mockk(relaxed = true)
    private val repository = MeetingRepositoryImpl(store)

    @Test
    fun test_getMeetingList_should_work(){

        //when:
        runBlocking { repository.getMeetingList(URL) }

        //then:
        coVerify { store.stream(any()) }

    }

    companion object {
        private const val URL = "meeting.list"
    }

}