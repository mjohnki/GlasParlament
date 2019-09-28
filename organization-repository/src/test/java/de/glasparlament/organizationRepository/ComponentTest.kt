package de.glasparlament.organizationRepository

import de.glasparlament.data.OrganizationList
import de.glasparlament.data.Transfer
import de.glasparlament.organizationRepository.di.organizationRepositoryModule
import io.mockk.coEvery
import io.mockk.every
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
import retrofit2.Retrofit

internal class ComponentTest : KoinTest {

    private val repository by inject<OrganizationRepository>()
    private val endpoint = mockk<OrganizationEndpoint>()
    private val retrofit = mockk<Retrofit>()

    @Test
    fun testGetOrganizationListWithError() {
        //given:
        startKoin { modules(listOf(organizationRepositoryModule, testModule)) }
        val url = "http://test.test"
        val errorBody = ResponseBody.create(MediaType.get("text/plain"), "Error")
        val response = Response.error<OrganizationList>(400, errorBody)
        setupMocks(response)

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
        setupMocks(response)

        //when:
        val result = runBlocking { repository.getOrganizationList(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(OrganizationList(), (result as Transfer.Success).data)
        stopKoin()
    }

    private fun setupMocks(response: Response<OrganizationList>){
        val  service : Class<OrganizationEndpoint> = OrganizationEndpoint::class.java
        every { retrofit.create(eq(service)) } returns endpoint
        coEvery { endpoint.getOrganizationList(any()) } returns response
    }

    private val testModule = module {
        single<Retrofit> { retrofit }
    }
}
