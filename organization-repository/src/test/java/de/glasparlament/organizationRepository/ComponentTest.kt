package de.glasparlament.organizationRepository

import de.glasparlament.data.OrganizationList
import de.glasparlament.data.Transfer
import de.glasparlament.organizationRepository.di.organizationRepositoryModule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Response

internal class ComponentTest : KoinTest {

    private val repository by inject<OrganizationRepository>()
    private val endpoint = mockk<OrganizationEndpoint>()

    @Test
    fun testGetOrganizationListWithError() {
        //given:
        startKoin { modules(listOf(organizationRepositoryModule, testModule)) }
        val url = "http://test.test"
        val errorBody = ResponseBody.create(MediaType.get("text/plain"), "Error")
        val erroResponse = Response.error<OrganizationList>(400, errorBody)
        coEvery { endpoint.getOrganizationList(url) } returns erroResponse

        //when:
        val result = runBlocking { repository.getOrganizationList(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(OrganizationApi.errorMessageOrganizationList, (result as Transfer.Error).exception)
        stopKoin()
    }

    @Test
    fun testGetMeetingListWithSuccess() {
        //given:
        startKoin { modules(listOf(organizationRepositoryModule, testModule)) }
        val url = "http://test.test"
        val response = Response.success(200, OrganizationList())
        coEvery { endpoint.getOrganizationList(url) } returns response

        //when:
        val result = runBlocking { repository.getOrganizationList(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(OrganizationList(), (result as Transfer.Success).data)
        stopKoin()
    }

    private val testModule = module {
        single<OrganizationEndpoint>(override = true) { endpoint }
    }
}