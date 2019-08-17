package de.glasparlament.meeting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.glasparlament.common_android.NavigationCommand
import de.glasparlament.data.Transfer
import de.glasparlament.organization.OrganizationListFragmentDirections
import de.glasparlament.test_lib.observeOnce
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

class MeetingViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private val useCase = mockk<MeetingListUseCase>()
    private lateinit var viewModel: MeetingViewModel

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
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
        // Check State Loading
        viewModel.uiModel.observeOnce {
            Assert.assertFalse(it.listVisibility)
            Assert.assertTrue(it.progressBarVisibility)
        }

        //when:
        viewModel.bind(url)

        //then:
        viewModel.uiModel.observeOnce {
            Assert.assertFalse(it.listVisibility)
            Assert.assertTrue(it.progressBarVisibility)
        }
    }

    @Test
    fun testUseCaseSuccess() {
        //given:
        val url = "http://test.test"
        val data = Transfer.Success(TestData.meetings)
        coEvery { useCase.execute(url) } returns data
        viewModel = MeetingViewModelImpl(useCase)
        // Check Loading State
        viewModel.uiModel.observeOnce {
            Assert.assertFalse(it.listVisibility)
            Assert.assertTrue(it.progressBarVisibility)
        }

        //when:
        viewModel.bind(url)
        Thread.sleep(1000)

        //then:
        //Loaded State
        viewModel.uiModel.observeOnce {
            Assert.assertTrue(it.listVisibility)
            Assert.assertFalse(it.progressBarVisibility)
            Assert.assertEquals(it.meetings, TestData.meetings)
        }
    }

    @Test
    fun testUseCaseNavigate() {
        //given:
        val url = "http://test.test"
        val data = Transfer.Success(TestData.meetings)
        coEvery { useCase.execute(url) } returns data
        val direction =
                MeetingListFragmentDirections.actionMeetingListFragmentToAgendaFragment("meetingId", "title")
        viewModel = MeetingViewModelImpl(useCase)

        //when:
        viewModel.navigate(direction)

        //then:
        viewModel.navigationCommand.observeOnce {
            Assert.assertTrue(it is NavigationCommand.To)
        }
    }

}