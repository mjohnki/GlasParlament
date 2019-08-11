package de.glasparlament.agendaitem_repository

import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AgendaItemRepositoryTest {

    private val api = mockk<AgendaItemApi>()
    private val repository = AgendaItemRepositoryImpl(api)

    @Test
    fun testGetAgendaItemWithError() {
        //given:
        val url = "http://test.test"
        val errorMessage = "error loading data"
        val response = Transfer.Error(errorMessage)
        coEvery { api.getAgendaItem(url) } returns response

       //when:
        val result = runBlocking {repository.getAgendaItem(url)}

        //then:
        assertTrue(result is Transfer.Error)
        assertEquals(errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetAgendaItemWithSuccess() {
        //given:
        val url = "http://test.test"
        val response = Transfer.Success(TestData.agendaItem)
        coEvery { api.getAgendaItem(url) } returns response

        //when:
        val result = runBlocking {repository.getAgendaItem(url)}

        //then:
        assertTrue(result is Transfer.Success)
        assertEquals(TestData.agendaItem, (result as Transfer.Success).data)
    }
}
