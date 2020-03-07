package de.glasparlament.meeting

import de.glasparlament.data.Transfer
import de.glasparlament.repository.meeting.Meeting
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class MeetingViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val useCase = mockk<MeetingListUseCase>()
    private lateinit var viewModel: MeetingViewModel

    @BeforeEach
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testUseCaseError() {
        //given:
        val url = "http://test.test"
        val errorMessage = "Error Loading Data"
        val data = Transfer.Error(errorMessage)
        coEvery { useCase.execute(url) } returns data
        viewModel = MeetingViewModelImpl(useCase)

        //when:
        viewModel.bind(url)
        Thread.sleep(200)


        //then:
        Assertions.assertTrue(viewModel.state.value is MeetingViewModel.State.Error)
    }

    @Test
    fun testUseCaseSuccess() {
        //given:
        val url = "http://test.test"
        val meeting = Meeting(
                id = "id",
                name = "39. Sitzung des Plenums",
                agendaItem = listOf(),
                body = "http://test.test"
        )
        val data = Transfer.Success(listOf(meeting))
        coEvery { useCase.execute(url) } returns data
        viewModel = MeetingViewModelImpl(useCase)

        //when:
        viewModel.bind(url)
        Thread.sleep(200)

        //then:
        Assertions.assertTrue(viewModel.state.value is MeetingViewModel.State.Loaded)
    }
}