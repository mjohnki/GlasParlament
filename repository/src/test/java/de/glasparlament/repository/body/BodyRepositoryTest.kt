package de.glasparlament.repository.body

import com.dropbox.android.external.store4.Store
import de.glasparlament.repository.meeting.Meeting
import de.glasparlament.repository.meeting.MeetingRepositoryImpl
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class BodyRepositoryTest {
    private val store: Store<String, List<Body>> = mockk(relaxed = true)
    private val repository = BodyRepositoryImpl(store)

    @Test
    fun test_getBodyList_should_work(){

        //when:
        runBlocking { repository.getBodyList() }

        //then:
        coVerify { store.stream(any()) }

    }
}