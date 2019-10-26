package de.glasparlament.agendaItemRepository

import de.glasparlament.agendaItemRepository.di.DaggerTestApplicationComponent
import de.glasparlament.data.Transfer
import de.glasparlament.data.db.*
import de.glasparlament.data.db.AgendaItem
import de.glasparlament.data.db.File
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

class ComponentTest {

    @Inject
    lateinit var repository: AgendaItemRepository

    @Inject
    lateinit var agendaItemDao: AgendaItemDao

    @Inject
    lateinit var meetingDao: MeetingDao

    @BeforeEach
    fun setUp() {
        DaggerTestApplicationComponent.builder()
                .build().inject(this)
    }

    @Test
    fun test_getAgendaItem_should_work() {

        //when:
        val result = runBlocking { repository.getAgendaItem("http://test.test") }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals("123", (result as Transfer.Success).data.id)
    }

    @Test
    fun test_getAgendaItems_should_work() {

        //when:
        val result = runBlocking { repository.getAgendaItems("http://test.test") }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(1, (result as Transfer.Success).data.size)
        Assertions.assertEquals("123", result.data[0].id)
    }

    @Test
    fun test_searchAgendaItems_should_work() {

        //when:
        val result = runBlocking { repository.searchAgendaItems("holz") }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(1, (result as Transfer.Success).data.size)
        Assertions.assertEquals("123", result.data[0].id)
    }
}