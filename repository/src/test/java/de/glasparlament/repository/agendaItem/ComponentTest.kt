package de.glasparlament.repository.agendaItem

import com.dropbox.android.external.store4.StoreResponse
import de.glasparlament.repository.agendaItem.di.DaggerTestApplicationComponent
import de.glasparlament.repository.agendaItem.local.AgendaItemDao
import de.glasparlament.repository.meeting.local.meeting.MeetingDao
import io.mockk.coVerify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ComponentTest {

    @Inject
    lateinit var repository: AgendaItemRepository

    @Inject
    lateinit var agendaItemDao: AgendaItemDao

    @Inject
    lateinit var meetingDao: MeetingDao

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
    fun test_getAgendaItem_should_work() {
        //given:
        val url =  "http://test.test"
        var receivedDataResponse = false

        //when:
       runBlocking {
            repository.getAgendaItem(url).take(2).collect {
                when(it) {
                    is StoreResponse.Data -> receivedDataResponse = true
                }
            }
        }

        //then:
        coVerify { agendaItemDao.getAgendaItem(url) }
        assert(receivedDataResponse)
    }

    @Test
    fun test_getAgendaItems_should_work() {
        //given:
        val meeting =  "test"
        var receivedDataResponse = false

        //when:
       runBlocking {
           repository.getAgendaItems(meeting).take(2).collect {
               when(it) {
                   is StoreResponse.Data -> receivedDataResponse = true
               }
           }
       }

        //then:
        coVerify { agendaItemDao.getAgendaItems(meeting) }
        assert(receivedDataResponse)
    }

    @Test
    fun test_searchAgendaItems_should_work() {
        //given:
        val search = "wood"
        var receivedDataResponse = false

        //when:
        runBlocking {
            repository.searchAgendaItems(search).take(2).collect {
                when(it) {
                    is StoreResponse.Data -> receivedDataResponse = true
                }
            }
        }

        //then:
        coVerify { agendaItemDao.searchtAgendaItems("%$search%") }
        assert(receivedDataResponse)
    }
}