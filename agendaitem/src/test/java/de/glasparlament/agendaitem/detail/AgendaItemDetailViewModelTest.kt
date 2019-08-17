package de.glasparlament.agendaitem.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.glasparlament.agendaitem.TestData
import de.glasparlament.data.Transfer
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

class AgendaItemDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private val useCase = mockk<AgendaItemUseCase>()

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
        val viewModel = AgendaItemDetailViewModelImpl(useCase)

        //when:
        viewModel.bind(url)

        //then:
        Assert.assertNull(viewModel.uiModel.value!!.agendaItem)
    }

    @Test
    fun testUseCaseSuccess() {
        //given:
        val url = "http://test.test"
        val data = Transfer.Success(TestData.agendaItem_16)
        coEvery { useCase.execute(url) } returns data
        val viewModel = AgendaItemDetailViewModelImpl(useCase)

        //when:
        viewModel.bind(url)
        Thread.sleep(1000)

        //then:
        //Loaded State
        Assert.assertNull(viewModel.uiModel.value)
    }
}