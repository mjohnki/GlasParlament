package de.glasparlament.meeting

import com.dropbox.android.external.store4.ResponseOrigin
import com.dropbox.android.external.store4.StoreResponse
import de.glasparlament.repository.meeting.Meeting
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.lang.IllegalArgumentException

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
    fun test_UseCase_returns_data_state() {
        //given:
        val url = "http://test.test"
        val meeting = Meeting(
                id = "id",
                name = "39. Sitzung des Plenums",
                agendaItem = listOf(),
                body = "http://test.test"
        )
        coEvery { useCase.execute(url) } returns flow {
            emit(StoreResponse.Data(listOf(meeting), ResponseOrigin.Fetcher))
        }
        viewModel = MeetingViewModelImpl(useCase)

        //when:
        viewModel.bind(url)

        //then:
        Assertions.assertTrue(viewModel.state.getTestValue() is MeetingViewModel.State.Loaded)
    }

    @Test
    fun test_UseCase_returns_Loading_state() {
        //given:
        val url = "http://test.test"
        coEvery { useCase.execute(url) } returns flow {
            emit(StoreResponse.Loading<List<Meeting>>(ResponseOrigin.Fetcher))
        }
        viewModel = MeetingViewModelImpl(useCase)

        //when:
        viewModel.bind(url)

        //then:
        Assertions.assertTrue(viewModel.state.getTestValue() is MeetingViewModel.State.Loading)
    }

    @Test
    fun test_UseCase_returns_error_state() {
        //given:
        val url = "http://test.test"
        coEvery { useCase.execute(url) } returns flow {
            emit(StoreResponse.Error<List<Meeting>>(IllegalArgumentException(), ResponseOrigin.Fetcher))
        }
        viewModel = MeetingViewModelImpl(useCase)

        //when:
        viewModel.bind(url)

        //then:
        Assertions.assertTrue(viewModel.state.getTestValue() is MeetingViewModel.State.Error)
    }
}