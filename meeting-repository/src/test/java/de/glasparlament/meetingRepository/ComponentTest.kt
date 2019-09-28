package de.glasparlament.meetingRepository

import de.glasparlament.data.MeetingList
import de.glasparlament.data.Transfer
import de.glasparlament.data.db.AgendaItemDao
import de.glasparlament.data.db.FileDao
import de.glasparlament.data.db.MeetingDao
import de.glasparlament.meetingRepository.di.meetingRepositoryModule
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

    private val repository by inject<MeetingRepository>()
    private val endpoint = mockk<MeetingEndpoint>()
    private val retrofit = mockk<Retrofit>()
    private val meetingDao = mockk<MeetingDao>(relaxed = true)
    private val agendaItemDao = mockk<AgendaItemDao>(relaxed = true)
    private val fileDao = mockk<FileDao>(relaxed = true)

    @Test
    fun testGetOrganizationListWithError() {
        //given:
        startKoin { modules(listOf(meetingRepositoryModule, testModule)) }
        val url = "http://test.test"
        val errorBody = ResponseBody.create(MediaType.get("text/plain"), "Error")
        val response = Response.error<MeetingList>(400, errorBody)
        setupMocks(response)

        //when:
        val result = runBlocking { repository.getMeetingList(url) }

        //then:
        Assertions.assertTrue(result is Transfer.Error)
        Assertions.assertEquals(MeetingApi.errorMessageMeetingList, (result as Transfer.Error).exception)
        stopKoin()
    }

    @Test
    fun testGetMeetingListWithSuccess() {
        //given:
        startKoin { modules(listOf(meetingRepositoryModule, testModule)) }
        val url = "http://test.test"
        val response = Response.success(200, MeetingList())
        setupMocks(response)

        //when:
        val result = runBlocking { repository.getMeetingList(url) }

        //then:
        val expected : List<Meeting> = listOf()
        Assertions.assertTrue(result is Transfer.Success)
        Assertions.assertEquals(expected, (result as Transfer.Success).data)
        stopKoin()
    }

    private fun setupMocks(response: Response<MeetingList>){
        val  service : Class<MeetingEndpoint> = MeetingEndpoint::class.java
        every { retrofit.create(eq(service)) } returns endpoint
        coEvery { endpoint.getMeetingList(any()) } returns response
        coEvery { meetingDao.getMeetings() } returns listOf()
    }

    private val testModule = module {
        single<Retrofit> { retrofit }
        single<MeetingDao> { meetingDao }
        single<AgendaItemDao> { agendaItemDao }
        single<FileDao> { fileDao }
    }
}
