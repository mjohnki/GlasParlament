package de.glasparlament.agendaitem.overview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.glasparlament.agendaitem.TestData
import de.glasparlament.common_android.NavigationCommand
import de.glasparlament.data.Transfer
import de.glasparlament.meeting.MeetingListFragmentDirections
import de.glasparlament.test_lib.observeOnce
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

class AgendaItemViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private val useCase = mockk<AgendaItemListUseCase>()
    private lateinit var viewModel: AgendaItemViewModel

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
        viewModel = AgendaItemViewModelImpl(useCase)
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
        val data = Transfer.Success(listOf(TestData.agendaItem_16))
        coEvery { useCase.execute(url) } returns data
        viewModel = AgendaItemViewModelImpl(useCase)
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
            Assert.assertEquals(it.agendaItems, listOf(TestData.agendaItem_16))
        }
    }
}