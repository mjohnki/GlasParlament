package de.glasparlament.meeting

import de.glasparlament.repository.meeting.MeetingRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class MeetingListUseCaseTest {

    private val repository: MeetingRepository = mockk(relaxed = true)
    private val useCase = MeetingListUseCase(repository)

    @Test
    fun test_UseCase_works() {
        //given:
        val url = "http://test.test"

        //when:
        runBlocking { useCase.execute(url) }

        //then:
        coEvery { repository.getMeetingList(url) }
    }
}