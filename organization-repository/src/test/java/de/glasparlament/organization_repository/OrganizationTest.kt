package de.glasparlament.organization_repository

import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class OrganizationTest {

    private val api = mockk<OrganizationApi>()
    private val repository: OrganizationRepository = OrganizationRepositoryImpl(api)

    @Test
    fun testGetMeetingWithError() {
        //given:
        val url = "http://test.test"
        val errorMessage = "error loading data"
        val transfer = Transfer.Error(errorMessage)
        coEvery { repository.getOrganizationList(url) } returns transfer

        //when:
        val result = runBlocking { repository.getOrganizationList(url) }

        //then:
        Assert.assertTrue(result is Transfer.Error)
        Assert.assertEquals(errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetMeetingWithSuccess() {
        //given:
        val url = "http://test.test"
        val transfer = Transfer.Success(TestData.organizationList)
        coEvery { repository.getOrganizationList(url) } returns transfer

        //when:
        val result = runBlocking { repository.getOrganizationList(url) }

        //then:
        Assert.assertTrue(result is Transfer.Success)
        Assert.assertEquals(TestData.organizationList, (result as Transfer.Success).data)
    }
}