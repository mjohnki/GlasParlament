package de.glasparlament.bodyRepository

import de.glasparlament.bodyRepository.di.DaggerTestApplicationComponent
import de.glasparlament.data.BodyList
import de.glasparlament.data.Transfer
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import javax.inject.Inject

class ComponentTest {

    @Inject
    lateinit var repository :BodyRepository

    @Inject
    lateinit var endpoint :BodyEndpoint

    @BeforeEach
    fun setUp() {
        DaggerTestApplicationComponent.builder()
                .build().inject(this)
    }

    @Test
    fun testGetOrganizationListWithError() {
        //given:
        val errorBody = ResponseBody.create(MediaType.get("text/plain"), "Error")
        val response = Response.error<BodyList>(400, errorBody)
        setupMocks(response)

        //when:
        val result = runBlocking { repository.getBodyList() }

        //then:
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(BodyApi.errorMessage, (result as Transfer.Error).exception)
    }

    @Test
    fun testGetMeetingListWithSuccess() {
        //given:
        val response = Response.success(200, BodyList())
        setupMocks(response)

        //when:
        val result = runBlocking { repository.getBodyList() }

        //then:
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(BodyList(), (result as Transfer.Success).data)
    }

    private fun setupMocks(response: Response<BodyList>){
        coEvery { endpoint.getBodyList() } returns response
    }
}
