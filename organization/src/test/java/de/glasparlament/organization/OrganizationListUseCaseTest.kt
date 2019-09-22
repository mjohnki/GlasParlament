package de.glasparlament.organization

import de.glasparlament.body_repository.BodyRepository
import de.glasparlament.data.Transfer
import de.glasparlament.organization_repository.OrganizationRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OrganizationListUseCaseTest {

    private val organizationRepository = mockk<OrganizationRepository>()
    private val bodyRepository = mockk<BodyRepository>()
    private val useCase  = OrganizationListUseCase(organizationRepository, bodyRepository)

    @Test
    fun testUseCaseWithBodyListError() {
        //given:
        val errorMessage = "Error Loading Data"
        val bodyList = Transfer.Error(errorMessage)
        coEvery { bodyRepository.getBodyList() } returns  bodyList

        //when:
        val result = runBlocking {useCase.execute()}

        //then:
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testUseCaseWithOrganizationListError() {
        //given:
        val errorMessage = "Error Loading Data"
        val bodyList = Transfer.Success(TestData.bodyList)
        val organizationList = Transfer.Error(errorMessage)
        coEvery { bodyRepository.getBodyList() } returns  bodyList
        coEvery { organizationRepository.getOrganizationList(any()) } returns  organizationList

        //when:
        val result = runBlocking {useCase.execute()}

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals((result as Transfer.Success).data,  listOf<BodyOrganization>())
    }

    @Test
    fun testUseCaseWithOrganizationListSuccess() {
        //given:
        val bodyList = Transfer.Success(TestData.bodyList)
        val organizationList = Transfer.Success(TestData.organizationList)
        coEvery { bodyRepository.getBodyList() } returns  bodyList
        coEvery { organizationRepository.getOrganizationList(any()) } returns  organizationList

        //when:
        val result = runBlocking {useCase.execute()}

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals((result as Transfer.Success).data.size,  1)
    }
}