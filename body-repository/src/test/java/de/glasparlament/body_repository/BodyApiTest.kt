package de.glasparlament.body_repository

import de.glasparlament.data.BodyList
import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
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
        Assert.assertTrue(result is Transfer.Error)
        Assert.assertEquals(BodyApi.errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetBodyListWithSuccess() {
        //given:
        val response = Response.success(200,  TestData.bodyList)
        coEvery { endpoint.getBodyList() } returns response

        //when:
        val result = runBlocking {api.getBodyList()}

        //then:
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals(TestData.bodyList, (result as Transfer.Success).data)
    }
}