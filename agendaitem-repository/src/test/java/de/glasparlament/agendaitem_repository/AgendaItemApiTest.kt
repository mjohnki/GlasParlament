package de.glasparlament.agendaitem_repository

import de.glasparlament.data.AgendaItem
import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class AgendaItemApiTest {

    private val endpoint = mockk<AgendaItemEndpoint>()
    private val api = AgendaItemApi(endpoint)

    @Test
    fun testGetAgendaItemWithError() {
        //given:
        val url = "http://test.test"
        val errorBody = ResponseBody.create(MediaType.get("text/plain"), "Error")
        val response = Response.error<AgendaItem>(400,  errorBody)
        coEvery { endpoint.getAgendaItem(url) } returns response

       //when:
        val result = runBlocking {api.getAgendaItem(url)}

        //then:
        assertTrue(result is Transfer.Error)
        assertEquals(AgendaItemApi.errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetAgendaItemWithSuccess() {
        //given:
        val url = "http://test.test"
        val response = Response.success(200, TestData.agendaItem)
        coEvery { endpoint.getAgendaItem(url) } returns response

        //when:
        val result = runBlocking {api.getAgendaItem(url)}

        //then:
        assertTrue(result is Transfer.Success)
        assertEquals(TestData.agendaItem, (result as Transfer.Success).data)
    }
}
