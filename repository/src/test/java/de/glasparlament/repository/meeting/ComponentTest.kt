package de.glasparlament.repository.meeting

import com.dropbox.android.external.store4.StoreResponse
import de.glasparlament.repository.meeting.di.DaggerTestApplicationComponent
import de.glasparlament.repository.meeting.remote.MeetingEndpoint
import de.glasparlament.repository.meeting.remote.MeetingList
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ComponentTest {

    @Inject
    lateinit var repository: MeetingRepository

    @Inject
    lateinit var endpoint: MeetingEndpoint

    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        DaggerTestApplicationComponent.builder()
                .build().inject(this)
    }

    @AfterEach
    fun cleanup(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun test_GetMeetingList() {
        //given:
        val url = "http://test.test"
        coEvery { endpoint.getMeetingList(url) } returns MeetingList()
        var receivedDataResponse = false
        var receivedLoadingResponse = false

        //when:
        runBlocking {
            repository.getMeetingList(url).take(2).collect {
                when(it){
                    is StoreResponse.Data -> receivedDataResponse = true
                    is StoreResponse.Loading -> receivedLoadingResponse = true
                }
            }
        }

        //then:
        coVerify { endpoint.getMeetingList(url) }
        assert(receivedDataResponse)
        assert(receivedLoadingResponse)
    }

}



