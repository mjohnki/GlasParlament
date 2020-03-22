package de.glasparlament.repository.agendaItem

import com.dropbox.android.external.store4.Store
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class AgendaItemRepositoryTest {

    private val agendaItemSearchStore : Store<String, List<AgendaItemSearchResult>> = mockk(relaxed = true)
    private val agendaItemsStore : Store<String, List<AgendaItem>> = mockk(relaxed = true)
    private val agendaItemStore : Store<String, AgendaItem> = mockk(relaxed = true)
    private val repository = AgendaItemRepositoryImpl(agendaItemSearchStore, agendaItemsStore, agendaItemStore)

    @Test
    fun test_searchAgendaItems_should_work(){
        //given:
        val search = "search"

        //when:
        runBlocking { repository.searchAgendaItems(search) }

        //then:
        every { agendaItemSearchStore.stream(any()) }
    }

    @Test
    fun test_getAgendaItems_should_work(){
        //given:
        val meeting = "meeting"

        //when:
        runBlocking { repository.getAgendaItems(meeting) }

        //then:
        every { agendaItemsStore.stream(any()) }
    }

    @Test
    fun test_getAgendaItem_should_work(){
        //given:
        val url = "test.test"

        //when:
        runBlocking { repository.getAgendaItems(url) }

        //then:
        every { agendaItemStore.stream(any()) }
    }
}