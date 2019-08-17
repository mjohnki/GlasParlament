package de.glasparlament.meeting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.glasparlament.data.Transfer
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

        //when:
        viewModel.bind(url)
        Thread.sleep(200)


        //then:
        Assert.assertFalse(viewModel.uiModel.value!!.listVisibility)
        Assert.assertTrue(viewModel.uiModel.value!!.progressBarVisibility)
    }

    @Test
    fun testUseCaseSuccess() {
        //given:
        val url = "http://test.test"
        val data = Transfer.Success(TestData.meetings)
        coEvery { useCase.execute(url) } returns data
        viewModel = MeetingViewModelImpl(useCase)

        //when:
        viewModel.bind(url)
        Thread.sleep(200)

        //then:
        Assert.assertTrue(viewModel.uiModel.value!!.listVisibility)
        Assert.assertFalse(viewModel.uiModel.value!!.progressBarVisibility)
        Assert.assertEquals(viewModel.uiModel.value!!.meetings, TestData.meetings)
    }
}