package de.glasparlament.organization_repository

import de.glasparlament.data.OrganizationList
import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

class OrganizationApiTest {

    private val endpoint = mockk<OrganizationEndpoint>()
    private val api = OrganizationApi(endpoint)

    @Test
    fun testGetOrganizationListWithError() {
        //given:
        val url = "http://test.test"
        val errorBody = ResponseBody.create(MediaType.get("text/plain"), "Error")
        val response = Response.error<OrganizationList>(400,  errorBody)
        coEvery { endpoint.getOrganizationList(url) } returns  response

        //when:
        val result = runBlocking {api.getOrganizationList(url)}

        //then:
        Assert.assertTrue(result is Transfer.Error)
        Assert.assertEquals(OrganizationApi.errorMessageOrganizationList, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetMeetingListWithSuccess() {
        //given:
        val url = "http://test.test"
        val response = Response.success(200,  OrganizationList())
        coEvery { endpoint.getOrganizationList(url) } returns response

        //when:
        val result = runBlocking {api.getOrganizationList(url)}

        //then:
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals(OrganizationList(), (result as Transfer.Success).data)
    }
}