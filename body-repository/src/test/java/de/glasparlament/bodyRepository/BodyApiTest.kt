package de.glasparlament.bodyRepository

import de.glasparlament.data.BodyList
import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import retrofit2.Response

class BodyApiTest {

    private val endpoint = mockk<BodyEndpoint>()
    private val api = BodyApi(endpoint)

    @Test
    fun testGetBodyListWithError() {
        //given:
        val errorBody = ResponseBody.create(MediaType.get("text/plain"), "Error")
        val response = Response.error<BodyList>(400,  errorBody)
        coEvery { endpoint.getBodyList() } returns response

        //when:
        val result = runBlocking {api.getBodyList()}

        //then:
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(BodyApi.errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetBodyListWithSuccess() {
        //given:
        val response = Response.success(200,  TestData.bodyList)
        coEvery { endpoint.getBodyList() } returns response

        //when:
        val result = runBlocking {api.getBodyList()}

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(TestData.bodyList, (result as Transfer.Success).data)
    }
}