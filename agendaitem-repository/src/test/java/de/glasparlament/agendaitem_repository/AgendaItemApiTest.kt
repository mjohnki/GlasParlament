package de.glasparlament.agendaitem_repository

import de.glasparlament.data.AgendaItemRemote
import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import retrofit2.Response
import java.util.ArrayList

class AgendaItemApiTest {

    private val endpoint = mockk<AgendaItemEndpoint>()
    private val api = AgendaItemApi(endpoint)

    @Test
    fun testGetAgendaItemWithError() {
        //given:
        val url = "http://test.test"
        val errorBody = ResponseBody.create(MediaType.get("text/plain"), "Error")
        val response = Response.error<AgendaItemRemote>(400,  errorBody)
        coEvery { endpoint.getAgendaItem(url) } returns response

       //when:
        val result = runBlocking {api.getAgendaItem(url)}

        //then:
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(AgendaItemApi.errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetAgendaItemWithSuccess() {
        //given:
        val url = "http://test.test"
        val agendaItem = AgendaItemRemote("", "", "", "", ArrayList())
        val response = Response.success(200, agendaItem)
        coEvery { endpoint.getAgendaItem(url) } returns response

        //when:
        val result = runBlocking {api.getAgendaItem(url)}

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(agendaItem, (result as Transfer.Success).data)
    }
}
