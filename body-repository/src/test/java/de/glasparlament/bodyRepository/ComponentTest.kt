package de.glasparlament.bodyRepository

import de.glasparlament.bodyRepository.di.bodyRepositoryModule
import de.glasparlament.data.BodyList
import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Response
import retrofit2.Retrofit

internal class ComponentTest : KoinTest {

    private val repository by inject<BodyRepository>()
    private val endpoint = mockk<BodyEndpoint>()
    private val retrofit = mockk<Retrofit>()

    @Test
    fun testGetOrganizationListWithError() {
        //given:
        startKoin { modules(listOf(bodyRepositoryModule, testModule)) }
        val errorBody = ResponseBody.create(MediaType.get("text/plain"), "Error")
        val response = Response.error<BodyList>(400, errorBody)
        setupMocks(response)

        //when:
        val result = runBlocking { repository.getBodyList() }

        //then:
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(BodyApi.errorMessage, (result as Transfer.Error).exception)
        stopKoin()
    }

    @Test
    fun testGetMeetingListWithSuccess() {
        //given:
        startKoin { modules(listOf(bodyRepositoryModule, testModule)) }
        val response = Response.success(200, BodyList())
        setupMocks(response)

        //when:
        val result = runBlocking { repository.getBodyList() }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(BodyList(), (result as Transfer.Success).data)
        stopKoin()
    }

    private fun setupMocks(response: Response<BodyList>){
        val  service : Class<BodyEndpoint> = BodyEndpoint::class.java
        every { retrofit.create(eq(service)) } returns endpoint
        coEvery { endpoint.getBodyList() } returns response
    }

    private val testModule = module {
        single<Retrofit> { retrofit }
    }
}
