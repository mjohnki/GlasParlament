package de.glasparlament.organizationRepository

import de.glasparlament.data.OrganizationList
import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

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
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetMeetingWithSuccess() {
        //given:
        val url = "http://test.test"
        val transfer = Transfer.Success(OrganizationList())
        coEvery { repository.getOrganizationList(url) } returns transfer

        //when:
        val result = runBlocking { repository.getOrganizationList(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(OrganizationList(), (result as Transfer.Success).data)
    }
}